package kr.kro.jayden_bin.greenmate.entity.learning

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import kr.kro.jayden_bin.greenmate.entity.BaseTimeEntity

@Entity
@Table(
    name = "learnings",
)
@SequenceGenerator(name = "LEARNING_SEQ_GENERATOR", sequenceName = "LEARNING_SEQ", initialValue = 1, allocationSize = 1)
class Learning(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEARNING_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @Column(length = 50, nullable = false, updatable = false, unique = true)
    val title: String,
    @Column(length = 50, nullable = false, updatable = false)
    val iconImageName: String,
) : BaseTimeEntity() {
    @OneToMany(mappedBy = "learning")
    val steps: List<LearningStep> = mutableListOf()
}
