package kr.kro.jayden_bin.greenmate.controller.team.request

import kr.kro.jayden_bin.greenmate.dto.AreaDto
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class CreateTeamRecruitmentRequest(
    val title: String,
    val description: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dueDate: LocalDateTime,
    val area: AreaDto?,
)

data class Point(
    val latitude: Double,
    val longitude: Double,
)

enum class AreaType {
    CIRCLE,
    POLYGON,
}
