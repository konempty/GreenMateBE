package kr.kro.jayden_bin.greenmate.controller.team

import jakarta.validation.constraints.Size
import kr.kro.jayden_bin.greenmate.controller.team.request.CreateTeamRecruitmentRequest
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentDetailResponse
import kr.kro.jayden_bin.greenmate.controller.team.response.TeamRecruitmentListResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController("/api/v1/teams")
class TeamController {
    @ResponseStatus(CREATED)
    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun createTeamRecruitment(
        @RequestPart
        request: CreateTeamRecruitmentRequest,
        @Size(max = 5, message = "이미지는 최대 5개까지 업로드할 수 있습니다.")
        @RequestPart(required = false)
        reportPhotos: List<MultipartFile>?,
    ) {
        TODO()
    }

    @GetMapping
    fun getTeamRecruitmentList(): List<TeamRecruitmentListResponse> {
        TODO()
    }

    @GetMapping("/{recruitmentId}")
    fun getTeamRecruitmentDetail(
        @PathVariable
        recruitmentId: Long,
    ): TeamRecruitmentDetailResponse {
        TODO()
    }
}
