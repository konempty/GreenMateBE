package kr.kro.jayden_bin.greenmate.controller.community

import jakarta.validation.constraints.Size
import kr.kro.jayden_bin.greenmate.controller.community.request.CreateCommunityCommentRequest
import kr.kro.jayden_bin.greenmate.controller.community.request.CreateCommunityRequest
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityDetailResponse
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityLikeResponse
import kr.kro.jayden_bin.greenmate.controller.community.response.CommunityListResponse
import kr.kro.jayden_bin.greenmate.entity.user.User
import kr.kro.jayden_bin.greenmate.service.CommunityService
import org.springframework.http.HttpStatus.CREATED
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
@RequestMapping("/api/v1/communities")
class CommunityController(
    private val communityService: CommunityService,
) {
    @ResponseStatus(CREATED)
    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun createCommunity(
        user: User,
        @RequestPart
        request: CreateCommunityRequest,
        @Size(max = 5, message = "이미지는 최대 5개까지 업로드할 수 있습니다.")
        @RequestPart(required = false)
        reportPhotos: List<MultipartFile> = emptyList(),
    ) {
        communityService.createCommunity(user, request, reportPhotos)
    }

    @GetMapping
    fun getCommunityList(user: User): List<CommunityListResponse> = communityService.getCommunityList(user)

    @GetMapping("/{communityId}")
    fun getCommunityDetail(
        user: User,
        @PathVariable
        communityId: Long,
    ): CommunityDetailResponse = communityService.getCommunityDetail(communityId, user)

    @PostMapping("/{communityId}/like")
    fun likeCommunity(
        user: User,
        @PathVariable
        communityId: Long,
    ): CommunityLikeResponse = communityService.likeCommunity(user, communityId)

    @ResponseStatus(CREATED)
    @PostMapping("/{communityId}/comment")
    fun createComment(
        user: User,
        @PathVariable
        communityId: Long,
        @RequestBody
        request: CreateCommunityCommentRequest,
    ) = communityService.createComment(user, communityId, request)
}
