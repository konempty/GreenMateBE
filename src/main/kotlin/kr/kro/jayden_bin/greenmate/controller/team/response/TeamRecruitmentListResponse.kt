package kr.kro.jayden_bin.greenmate.controller.team.response

data class TeamRecruitmentListResponse(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: String,
    val status: TeamRecruitmentStatus,
)

enum class TeamRecruitmentStatus {
    RECRUITING,
    DEADLINE_SOON,
    CLOSED,
}
