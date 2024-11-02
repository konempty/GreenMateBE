package kr.kro.jayden_bin.greenmate.controller.team.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.kro.jayden_bin.greenmate.dto.AreaDto
import kr.kro.jayden_bin.greenmate.dto.CommentDto
import kr.kro.jayden_bin.greenmate.dto.UserSimpleDto
import java.time.LocalDateTime

data class TeamRecruitmentDetailResponse(
    val id: Long,
    val title: String,
    val description: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val dueDate: LocalDateTime,
    val area: AreaDto?,
    val imageUrls: List<String>,
    val user: UserSimpleDto,
    val comments: List<CommentDto>,
    val joinCount: Int,
    val isJoined: Boolean,
)
