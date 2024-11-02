package kr.kro.jayden_bin.greenmate.controller.learning

import kr.kro.jayden_bin.greenmate.controller.learning.response.LearningResponse
import kr.kro.jayden_bin.greenmate.service.LearningService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/learnings")
class LearningController(
    private val learningService: LearningService,
) {
    @GetMapping
    fun getLearningList(): List<LearningResponse> = learningService.getLearningList()
}
