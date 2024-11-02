package kr.kro.jayden_bin.greenmate.controller.user.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
)
