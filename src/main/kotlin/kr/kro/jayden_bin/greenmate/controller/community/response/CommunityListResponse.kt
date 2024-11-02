package kr.kro.jayden_bin.greenmate.controller.community.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import java.time.LocalDateTime

data class CommunityListResponse(
    val id: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val createdAt: LocalDateTime,
    val title: String,
    val description: String,
    val imageUrls: List<String>,
    val likeCount: Int,
    val commentCount: Int,
    val user: UserSimpleDto,
    val isLiked: Boolean,
)
