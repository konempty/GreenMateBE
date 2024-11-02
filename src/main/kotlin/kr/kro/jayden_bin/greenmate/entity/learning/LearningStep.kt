package kr.kro.jayden_bin.greenmate.entity.learning

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import kr.kro.jayden_bin.greenmate.entity.BaseTimeEntity

@Entity
@Table(
    name = "learning_steps",
)
@SequenceGenerator(name = "LEARNING_STEP_SEQ_GENERATOR", sequenceName = "LEARNING_STEP_SEQ", initialValue = 1, allocationSize = 1)
class LearningStep(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEARNING_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_id", updatable = false)
    val learning: Learning,
    @Column(length = 255, nullable = false, updatable = false)
    val content: String,
) : BaseTimeEntity()
