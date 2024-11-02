package kr.kro.jayden_bin.greenmate.controller.learning.response

data class LearningResponse(
    val id: Long,
    val iconImage: String,
    val title: String,
    val steps: List<String>,
)
