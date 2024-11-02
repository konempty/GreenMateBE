package kr.kro.jayden_bin.greenmate.controller.team.response

import kr.kro.jayden_bin.greenmate.dto.AreaDto
import kr.kro.jayden_bin.greenmate.dto.CommentDto
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TeamRecruitmentDetailResponse(
    val id: Long,
    val title: String,
    val description: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dueDate: LocalDateTime,
    val area: AreaDto?,
    val imageUrls: List<String>,
    val comments: List<CommentDto>,
)
