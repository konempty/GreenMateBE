package kr.kro.jayden_bin.greenmate.controller.team.response

import kr.kro.jayden_bin.greenmate.controller.team.request.Area
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class TeamRecruitmentDetailResponse(
    val title: String,
    val description: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dueDate: LocalDateTime,
    val area: Area?,
    val imageUrls: List<String>,
)
