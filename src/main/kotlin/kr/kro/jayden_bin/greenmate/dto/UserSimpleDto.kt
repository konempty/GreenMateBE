package kr.kro.jayden_bin.greenmate.dto

import kr.kro.jayden_bin.greenmate.entity.user.User

data class UserSimpleDto(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String,
) {
    companion object {
        fun of(
            user: User,
            profileImageUrl: String,
        ): UserSimpleDto =
            UserSimpleDto(
                id = user.id,
                nickname = user.nickname,
                profileImageUrl = profileImageUrl,
            )
    }
}
