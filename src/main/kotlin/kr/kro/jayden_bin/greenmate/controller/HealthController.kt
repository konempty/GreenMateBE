package kr.kro.jayden_bin.greenmate.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health")
    fun health(): HealthResponse = HealthResponse(status = HttpServletResponse.SC_OK)

    @GetMapping("/ready")
    fun ready() {
    }
}

data class HealthResponse(
    val status: Int,
)
