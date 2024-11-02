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
        if (userRepository.existsByNickname(signUpRequest.nickname)) {
            throw IllegalArgumentException("사용중인 닉네임입니다")
        }
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw IllegalArgumentException("사용중인 이메일입니다")
        }
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
        profileImage?.let { imageService.upload(fileName, profileImage) }
        return makeTokens(user)
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): ServiceTokensResponse {
        val user =
            userRepository.findByEmail(request.email)?.takeIf { passwordEncoder.matches(request.password, it.password) }
                ?: throw IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다")
        return makeTokens(user)
    }

    @Transactional(readOnly = true)
    fun refreshToken(refreshToken: String): ServiceTokensResponse {
        require(
            jwtUtil.validateToken(jwtUtil.refreshKey, refreshToken) &&
                jwtUtil.validateCachedRefreshTokenRotateId(refreshToken),
        ) {
            throw IllegalArgumentException("유효하지 않은 토큰입니다")
        }
        val userId = jwtUtil.getUserId(jwtUtil.refreshKey, refreshToken)
        val user = userRepository.findById(userId).get()
        return makeTokens(user)
    }

    private fun makeTokens(user: User): ServiceTokensResponse {
        val profileImage = imageService.getDownloadUrl(user.profileImageName)
        val accessToken = jwtUtil.generateAccessToken(user, profileImage)
        val rotateId = jwtUtil.generateRotateId()
        val refreshToken = jwtUtil.generateRefreshToken(user.id, rotateId)
        jwtUtil.storeCachedRefreshTokenRotateId(user.id, rotateId)
        return ServiceTokensResponse(accessToken, refreshToken)
    }
}
