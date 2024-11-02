package kr.kro.jayden_bin.greenmate.controller.learning

import kr.kro.jayden_bin.greenmate.controller.learning.response.LearningResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/learnings")
class LearningController {
    @GetMapping
    fun getLearningList(): List<LearningResponse> {
        TODO()
    }
}
