package kr.kro.jayden_bin.greenmate.service

import kr.kro.jayden_bin.greenmate.controller.learning.response.LearningResponse
import kr.kro.jayden_bin.greenmate.entity.learning.LearningRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LearningService(
    private val learningRepository: LearningRepository,
    private val imageService: ImageService,
) {
    @Transactional(readOnly = true)
    fun getLearningList(): List<LearningResponse> =
        learningRepository.findAll().map {
            LearningResponse(
                id = it.id,
                title = it.title,
                iconImage = imageService.getDownloadUrl(it.iconImageName),
                steps = it.steps.map { step -> step.content },
            )
        }
}
