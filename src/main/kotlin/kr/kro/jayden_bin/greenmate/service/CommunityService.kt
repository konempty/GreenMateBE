package kr.kro.jayden_bin.greenmate.service

import kr.kro.jayden_bin.greenmate.controller.community.request.CommentRequest
import kr.kro.jayden_bin.greenmate.controller.community.request.CreateCommunityRequest
import kr.kro.jayden_bin.greenmate.controller.community.response.CommentResponse
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityDetailResponse
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityLikeResponse
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityListResponse
import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import kr.kro.jayden_bin.greenmate.entity.community.Community
import kr.kro.jayden_bin.greenmate.entity.community.CommunityComment
import kr.kro.jayden_bin.greenmate.entity.community.CommunityCommentRepository
import kr.kro.jayden_bin.greenmate.entity.community.CommunityImage
import kr.kro.jayden_bin.greenmate.entity.community.CommunityImageRepository
import kr.kro.jayden_bin.greenmate.entity.community.CommunityLike
import kr.kro.jayden_bin.greenmate.entity.community.CommunityLikeRepository
import kr.kro.jayden_bin.greenmate.entity.community.CommunityRepository
import kr.kro.jayden_bin.greenmate.entity.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class CommunityService(
    private val communityRepository: CommunityRepository,
    private val communityImageRepository: CommunityImageRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val communityLikeRepository: CommunityLikeRepository,
    private val imageService: ImageService,
) {
    @Transactional
    fun createCommunity(
        user: User,
        request: CreateCommunityRequest,
        reportPhotos: List<MultipartFile>,
    ) {
        val community =
            communityRepository.save(
                Community(
                    title = request.title,
                    user = user,
                    description = request.description,
                ),
            )
        val generatorPhotoNameMap =
            reportPhotos.associateBy { imageService.generateImageName(it) }
        communityImageRepository.saveAll(
            generatorPhotoNameMap.map { (fileName, _) ->
                CommunityImage(
                    community = community,
                    fileName = fileName,
                )
            },
        )
        generatorPhotoNameMap.forEach { (fileName, file) ->
            imageService.upload(fileName, file)
        }
    }

    @Transactional(readOnly = true)
    fun getCommunityList(): List<CommunityListResponse> {
        val defaultProfile = imageService.getDownloadUrl("defaultProfile.png")
        return communityRepository.findAll().map { community ->
            val images = communityImageRepository.findByCommunity(community)
            CommunityListResponse(
                id = community.id,
                title = community.title,
                description = community.description,
                user = UserSimpleDto.of(community.user, defaultProfile),
                imageUrls =
                    images.map {
                        imageService.getDownloadUrl(it.fileName)
                    },
                likeCount = communityLikeRepository.countByCommunity(community),
                commentCount = communityCommentRepository.countByCommunity(community),
                createdAt = community.createdAt,
            )
        }
    }

    @Transactional(readOnly = true)
    fun getCommunityDetail(communityId: Long): CommunityDetailResponse {
        val community = communityRepository.findById(communityId).get()
        val defaultProfile = imageService.getDownloadUrl("defaultProfile.png")
        return CommunityDetailResponse(
            id = community.id,
            title = community.title,
            description = community.description,
            user = UserSimpleDto.of(community.user, defaultProfile),
            imageUrls =
                communityImageRepository.findByCommunity(community).map {
                    imageService.getDownloadUrl(it.fileName)
                },
            likeCount = communityLikeRepository.countByCommunity(community),
            commentCount = communityCommentRepository.countByCommunity(community),
            createdAt = community.createdAt,
            comments =
                communityCommentRepository.findByCommunity(community).map {
                    CommentResponse(
                        id = it.id,
                        content = it.content,
                        createdAt = it.createdAt,
                        user = UserSimpleDto.of(it.user, defaultProfile),
                    )
                },
        )
    }

    @Transactional
    fun likeCommunity(
        user: User,
        communityId: Long,
    ): CommunityLikeResponse {
        val community = communityRepository.findById(communityId).get()
        val like = communityLikeRepository.findByCommunityAndUser(community, user)
        if (like != null) {
            communityLikeRepository.delete(like)
            return CommunityLikeResponse(communityLikeRepository.countByCommunity(community) - 1, false)
        } else {
            communityLikeRepository.save(CommunityLike(user = user, community = community))
            return CommunityLikeResponse(communityLikeRepository.countByCommunity(community) + 1, true)
        }
    }

    @Transactional
    fun createComment(
        user: User,
        communityId: Long,
        request: CommentRequest,
    ) {
        val community = communityRepository.findById(communityId).get()
        communityCommentRepository.save(
            CommunityComment(
                user = user,
                community = community,
                content = request.content,
            ),
        )
    }
}
