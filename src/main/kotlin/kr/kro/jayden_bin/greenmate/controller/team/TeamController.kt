package kr.kro.jayden_bin.greenmate.controller.team

import jakarta.validation.constraints.Size
import kr.kro.jayden_bin.greenmate.controller.team.request.CreateTeamCommentRequest
import kr.kro.jayden_bin.greenmate.controller.team.request.CreateTeamRecruitmentRequest
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamJoinResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentDetailResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentListResponse
import kr.kro.jayden_bin.greenmate.entity.user.User
import kr.kro.jayden_bin.greenmate.service.TeamService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/teams")
class TeamController(
    private val teamService: TeamService,
) {
    @ResponseStatus(CREATED)
    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun createTeamRecruitment(
        user: User,
        @RequestPart
        request: CreateTeamRecruitmentRequest,
        @Size(max = 5, message = "이미지는 최대 5개까지 업로드할 수 있습니다.")
        @RequestPart(required = false)
        reportPhotos: List<MultipartFile> = emptyList(),
    ) = teamService.createTeamRecruitment(user, request, reportPhotos)

    @GetMapping
    fun getTeamRecruitmentList(): List<TeamRecruitmentListResponse> = teamService.getTeamRecruitmentList()

    @GetMapping("/{recruitmentId}")
    fun getTeamRecruitmentDetail(
        @PathVariable
        recruitmentId: Long,
    ): TeamRecruitmentDetailResponse = teamService.getTeamRecruitmentDetail(recruitmentId)

    @ResponseStatus(NO_CONTENT)
    @PostMapping("/{recruitmentId}/join")
    fun joinTeamRecruitment(
        user: User,
        @PathVariable
        recruitmentId: Long,
    ): TeamJoinResponse = teamService.joinTeamRecruitment(user, recruitmentId)

    @ResponseStatus(CREATED)
    @PostMapping("/{recruitmentId}/comment")
    fun commentTeamRecruitment(
        user: User,
        @PathVariable
        recruitmentId: Long,
        @RequestBody
        request: CreateTeamCommentRequest,
    ) = teamService.commentTeamRecruitment(user, recruitmentId, request)
}
