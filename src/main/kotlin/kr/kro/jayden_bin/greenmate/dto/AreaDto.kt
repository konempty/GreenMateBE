package kr.kro.jayden_bin.greenmate.dto

import kr.kro.jayden_bin.greenmate.controller.team.request.AreaType
import kr.kro.jayden_bin.greenmate.controller.team.request.Point

data class AreaDto(
    val type: AreaType,
    val center: Point?,
    val radius: Double?,
    val points: List<Point>?,
)
