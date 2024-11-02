package kr.kro.jayden_bin.greenmate.service

import kr.kro.jayden_bin.greenmate.controller.user.request.LoginRequest
import kr.kro.jayden_bin.greenmate.controller.user.request.SignUpRequest
import kr.kro.jayden_bin.greenmate.controller.user.response.NicknameDuplicateCheckResponse
import kr.kro.jayden_bin.greenmate.controller.user.response.ServiceTokensResponse
import kr.kro.jayden_bin.greenmate.entity.user.User
import kr.kro.jayden_bin.greenmate.entity.user.UserRepository
import kr.kro.jayden_bin.greenmate.util.jwt.JwtUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val imageService: ImageService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun checkNicknameDuplicate(nickname: String): NicknameDuplicateCheckResponse =
        NicknameDuplicateCheckResponse(
            isDuplicate = userRepository.existsByNickname(nickname),
        )

    @Transactional
    fun signUp(
        signUpRequest: SignUpRequest,
        profileImage: MultipartFile?,
    ): ServiceTokensResponse {
        val fileName = profileImage?.let { imageService.generateImageName(it) } ?: "defaultProfile.png"
        val hashedPassword = passwordEncoder.encode(signUpRequest.password) // 비밀번호 해싱
        val user =
            userRepository.save(
                User(
                    nickname = signUpRequest.nickname,
                    email = signUpRequest.email,
                    password = hashedPassword,
                    profileImageName = fileName,
                ),
            )
        if (userRepository.existsByNickname(signUpRequest.nickname)) {
            throw IllegalArgumentException("Nickname is already in use")
        }
        profileImage?.let { imageService.upload(fileName, profileImage) }
        return makeTokens(user.id)
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): ServiceTokensResponse {
        val user =
            userRepository.findByEmail(request.email)?.takeIf { passwordEncoder.matches(request.password, it.password) }
                ?: throw IllegalArgumentException("Invalid email or password")
        return makeTokens(user.id)
    }

    @Transactional(readOnly = true)
    fun refreshToken(refreshToken: String): ServiceTokensResponse {
        require(
            jwtUtil.validateToken(jwtUtil.refreshKey, refreshToken) &&
                jwtUtil.validateCachedRefreshTokenRotateId(refreshToken),
        ) {
            throw IllegalArgumentException("Invalid refresh token")
        }
        val userId = jwtUtil.getUserId(jwtUtil.refreshKey, refreshToken)
        val user = userRepository.findById(userId).get()
        return makeTokens(user.id)
    }

    private fun makeTokens(userId: Long): ServiceTokensResponse {
        val accessToken = jwtUtil.generateAccessToken(userId)
        val rotateId = jwtUtil.generateRotateId()
        val refreshToken = jwtUtil.generateRefreshToken(userId, rotateId)
        jwtUtil.storeCachedRefreshTokenRotateId(userId, rotateId)
        return ServiceTokensResponse(accessToken, refreshToken)
    }
}
