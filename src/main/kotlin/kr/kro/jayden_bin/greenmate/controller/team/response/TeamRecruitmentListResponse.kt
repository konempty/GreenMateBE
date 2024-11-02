package kr.kro.jayden_bin.greenmate.controller.team.response

import java.time.LocalDateTime

data class TeamRecruitmentListResponse(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: LocalDateTime,
    val jointCount: Int,
    val status: TeamRecruitmentStatus,
)

enum class TeamRecruitmentStatus {
    RECRUITING,
    DEADLINE_SOON,
    CLOSED,
}
