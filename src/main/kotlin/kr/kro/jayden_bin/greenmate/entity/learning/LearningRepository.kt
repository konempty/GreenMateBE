package kr.kro.jayden_bin.greenmate.entity.learning

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LearningRepository : JpaRepository<Learning, Long>
