package kr.kro.jayden_bin.greenmate.service

import kr.kro.jayden_bin.greenmate.controller.team.request.CreateTeamCommentRequest
import kr.kro.jayden_bin.greenmate.controller.team.request.CreateTeamRecruitmentRequest
import kr.kro.jayden_bin.greenmate.controller.team.request.Point
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamJoinResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentDetailResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentListResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentStatus
import kr.kro.jayden_bin.greenmate.dto.AreaDto
import kr.kro.jayden_bin.greenmate.dto.CommentDto
import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import kr.kro.jayden_bin.greenmate.entity.team.Area
import kr.kro.jayden_bin.greenmate.entity.team.Team
import kr.kro.jayden_bin.greenmate.entity.team.TeamComment
import kr.kro.jayden_bin.greenmate.entity.team.TeamCommentRepository
import kr.kro.jayden_bin.greenmate.entity.team.TeamImage
import kr.kro.jayden_bin.greenmate.entity.team.TeamImageRepository
import kr.kro.jayden_bin.greenmate.entity.team.TeamJoin
import kr.kro.jayden_bin.greenmate.entity.team.TeamJoinRepository
import kr.kro.jayden_bin.greenmate.entity.team.TeamRepository
import kr.kro.jayden_bin.greenmate.entity.user.User
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService

@Service
class TeamService(
    private val teamRepository: TeamRepository,
    private val teamImageRepository: TeamImageRepository,
    private val imageService: ImageService,
    private val executor: ExecutorService,
    private val teamJoinRepository: TeamJoinRepository,
    private val teamCommentRepository: TeamCommentRepository,
) {
    @Transactional
    fun createTeamRecruitment(
        user: User,
        request: CreateTeamRecruitmentRequest,
        reportPhotos: List<MultipartFile>,
    ) {
        val team =
            teamRepository.save(
                Team(
                    user = user,
                    title = request.title,
                    description = request.description,
                    dueDate = request.dueDate,
                    area =
                        request.area?.let { area ->
                            Area(
                                type = area.type,
                                latitude = area.center?.latitude,
                                longitude = area.center?.longitude,
                                radius = area.radius,
                                points =
                                    area.points?.let { points ->
                                        GeometryFactory().createPolygon(points.map { Coordinate(it.latitude, it.longitude) }.toTypedArray())
                                    },
                            )
                        },
                ),
            )
        val generatorPhotoNameMap = reportPhotos.associateBy { imageService.generateImageName(it) }
        generatorPhotoNameMap
            .map {
                TeamImage(team = team, fileName = it.key)
            }.also { teamImageRepository.saveAll(it) }
        generatorPhotoNameMap
            .map {
                CompletableFuture.supplyAsync({
                    imageService.upload(
                        it.key,
                        it.value,
                    )
                }, executor)
            }.forEach { it.join() }
    }

    @Transactional(readOnly = true)
    fun getTeamRecruitmentList(): List<TeamRecruitmentListResponse> =
        teamRepository.findAll().map {
            TeamRecruitmentListResponse(
                id = it.id,
                title = it.title,
                description = it.description,
                dueDate = it.dueDate,
                joinCount = teamJoinRepository.countByTeam(it),
                status =
                    if (it.dueDate.isBefore(LocalDateTime.now())) {
                        TeamRecruitmentStatus.CLOSED
                    } else if (it.dueDate.isBefore(LocalDateTime.now().plusDays(1))) {
                        TeamRecruitmentStatus.DEADLINE_SOON
                    } else {
                        TeamRecruitmentStatus.RECRUITING
                    },
            )
        }

    @Transactional(readOnly = true)
    fun getTeamRecruitmentDetail(
        recruitmentId: Long,
        user: User,
    ): TeamRecruitmentDetailResponse {
        val team = teamRepository.findById(recruitmentId).get()
        return TeamRecruitmentDetailResponse(
            id = team.id,
            title = team.title,
            description = team.description,
            dueDate = team.dueDate,
            area =
                team.area?.let { area ->
                    AreaDto(
                        type = area.type,
                        center =
                            area.latitude?.let { latitude ->
                                area.longitude?.let { longitude ->
                                    Point(latitude, longitude)
                                }
                            },
                        radius = area.radius,
                        points = area.points?.coordinates?.map { coordinate -> Point(coordinate.x, coordinate.y) },
                    )
                },
            imageUrls =
                teamImageRepository
                    .findByTeam(team)
                    .map {
                        CompletableFuture.supplyAsync { imageService.getDownloadUrl(it.fileName) }
                    }.map { it.join() },
            user = UserSimpleDto.of(team.user, imageService.getDownloadUrl(team.user.profileImageName)),
            comments =
                teamCommentRepository.findByTeam(team).map {
                    CommentDto(
                        id = it.id,
                        user = UserSimpleDto.of(it.user, imageService.getDownloadUrl(it.user.profileImageName)),
                        content = it.content,
                        createdAt = it.createdAt,
                    )
                },
            joinCount = teamJoinRepository.countByTeam(team),
            isJoined = teamJoinRepository.existsByTeamAndUser(team, user),
        )
    }

    @Transactional
    fun joinTeamRecruitment(
        user: User,
        recruitmentId: Long,
    ): TeamJoinResponse {
        val team = teamRepository.findById(recruitmentId).get()
        val teamJoin = teamJoinRepository.findByTeamAndUser(team, user)
        if (teamJoin != null) {
            teamJoinRepository.delete(teamJoin)
            return TeamJoinResponse(
                joinCount = teamJoinRepository.countByTeam(team),
                isJoin = false,
            )
        } else {
            teamJoinRepository.save(TeamJoin(team = team, user = user))
            return TeamJoinResponse(
                joinCount = teamJoinRepository.countByTeam(team),
                isJoin = true,
            )
        }
    }

    @Transactional
    fun commentTeamRecruitment(
        user: User,
        recruitmentId: Long,
        request: CreateTeamCommentRequest,
    ) {
        val team = teamRepository.findById(recruitmentId).get()
        teamCommentRepository.save(
            TeamComment(
                user = user,
                team = team,
                content = request.content,
            ),
        )
    }
}
