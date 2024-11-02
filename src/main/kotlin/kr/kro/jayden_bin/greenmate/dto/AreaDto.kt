package kr.kro.jayden_bin.greenmate.dto

data class Point(
    val latitude: Double,
    val longitude: Double,
)

enum class AreaType {
    CIRCLE,
    POLYGON,
}

data class AreaDto(
    val type: AreaType?,
    val center: Point?,
    val radius: Double?,
    val points: List<Point>?,
)
