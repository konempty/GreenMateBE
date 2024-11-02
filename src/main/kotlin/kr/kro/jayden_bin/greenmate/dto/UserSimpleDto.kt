package kr.kro.jayden_bin.greenmate.dto

import kr.kro.jayden_bin.greenmate.entity.user.User

data class UserSimpleDto(
    val id: Long,
    val nickName: String,
    val profileImageUrl: String,
) {
    companion object {
        fun of(user: User): UserSimpleDto =
            UserSimpleDto(
                id = user.id,
                nickName = user.nickname,
                profileImageUrl = user.profileImageName,
            )
    }
}
