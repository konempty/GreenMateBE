package kr.kro.jayden_bin.greenmate.controller.learning.response

data class LearningResponse(
    val id: Long,
    val title: String,
    val iconImage: String,
    val steps: List<String>,
)
