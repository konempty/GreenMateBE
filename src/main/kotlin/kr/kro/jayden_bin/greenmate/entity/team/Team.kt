package kr.kro.jayden_bin.greenmate.entity.team

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import kr.kro.jayden_bin.greenmate.controller.team.request.AreaType
import kr.kro.jayden_bin.greenmate.entity.BaseTimeEntity
import kr.kro.jayden_bin.greenmate.entity.user.User
import org.locationtech.jts.geom.Polygon
import java.time.LocalDateTime

@Entity
@Table(
    name = "teams",
)
@SequenceGenerator(name = "TEAM_SEQ_GENERATOR", sequenceName = "TEAM_SEQ", initialValue = 1, allocationSize = 1)
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    val user: User,
    @Column(length = 50, nullable = false, updatable = false)
    val title: String,
    @Column(nullable = false, columnDefinition = "clob")
    val description: String,
    @Column(nullable = false)
    val dueDate: LocalDateTime,
    @Embedded
    val area: Area?,
) : BaseTimeEntity()

@Embeddable
class Area(
    @Column(nullable = false)
    val type: AreaType,
    @Column(nullable = true)
    val latitude: Double?,
    @Column(nullable = true)
    val longitude: Double?,
    @Column(nullable = true)
    val radius: Double?,
    @Column(nullable = true, columnDefinition = "SDO_GEOMETRY")
    val points: Polygon?,
)
