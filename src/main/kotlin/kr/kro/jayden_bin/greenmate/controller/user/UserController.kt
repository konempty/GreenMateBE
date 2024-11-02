package kr.kro.jayden_bin.greenmate.controller.user

import kr.kro.jayden_bin.greenmate.controller.user.request.SignUpRequest
import kr.kro.jayden_bin.greenmate.controller.user.response.ServiceTokensResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.MultipartFile

@Controller("/api/v1/users")
class UserController {
    @ResponseStatus(CREATED)
    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun signUp(
        @RequestPart
        signUpRequest: SignUpRequest,
        @RequestPart(required = false)
        profileImage: MultipartFile?,
    ): ServiceTokensResponse {
        TODO()
    }

    @ResponseStatus(NO_CONTENT)
    @GetMapping("/nickname-duplicate")
    fun checkNicknameDuplicate(
        @RequestParam
        nickname: String,
    ) {
        TODO()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody
        request: SignUpRequest,
    ): ServiceTokensResponse {
        TODO()
    }
}
