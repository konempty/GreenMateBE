package kr.kro.jayden_bin.greenmate.controller.community.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import java.time.LocalDateTime

data class CommunityDetailResponse(
    val id: Long,
    val title: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val createdAt: LocalDateTime,
    val user: UserSimpleDto,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val imageUrls: List<String>,
    val comments: List<CommentResponse>,
)

data class CommentResponse(
    val id: Long,
    val content: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val createdAt: LocalDateTime,
    val user: UserSimpleDto,
)
