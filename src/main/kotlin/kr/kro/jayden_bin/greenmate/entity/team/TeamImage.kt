package kr.kro.jayden_bin.greenmate.entity.team

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
    name = "team_images",
)
@SequenceGenerator(name = "TEAM_IMAGE_SEQ_GENERATOR", sequenceName = "TEAM_IMAGE_SEQ", initialValue = 1, allocationSize = 1)
class TeamImage(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_IMAGE_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", updatable = false, nullable = false)
    val team: Team,
    @Column(length = 50, nullable = false)
    val fileName: String,
) : BaseTimeEntity()
