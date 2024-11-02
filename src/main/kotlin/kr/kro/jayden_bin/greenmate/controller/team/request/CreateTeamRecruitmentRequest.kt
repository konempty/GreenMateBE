package kr.kro.jayden_bin.greenmate.controller.team.request

import com.fasterxml.jackson.annotation.JsonFormat
import kr.kro.jayden_bin.greenmate.dto.AreaDto
import java.time.LocalDateTime

data class CreateTeamRecruitmentRequest(
    val title: String,
    val description: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
