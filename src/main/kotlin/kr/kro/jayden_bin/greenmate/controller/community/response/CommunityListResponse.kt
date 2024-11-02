package kr.kro.jayden_bin.greenmate.controller.community.response

import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import java.time.LocalDateTime

data class CommunityListResponse(
    val id: Long,
    val createdAt: LocalDateTime,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val likeCount: Int,
    val commentCount: Int,
    val user: UserSimpleDto,
)
