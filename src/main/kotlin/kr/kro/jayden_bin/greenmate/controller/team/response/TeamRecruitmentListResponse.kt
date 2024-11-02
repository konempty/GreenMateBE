package kr.kro.jayden_bin.greenmate.controller.team.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TeamRecruitmentListResponse(
    val id: Long,
    val title: String,
    val description: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    val dueDate: LocalDateTime,
    val joinCount: Int,
    val status: TeamRecruitmentStatus,
)

enum class TeamRecruitmentStatus {
    RECRUITING,
    DEADLINE_SOON,
    CLOSED,
}
