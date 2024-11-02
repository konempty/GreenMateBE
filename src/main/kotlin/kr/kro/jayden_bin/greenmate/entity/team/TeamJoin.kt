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
import kr.kro.jayden_bin.greenmate.entity.user.User

@Entity
@Table(
    name = "team_joins",
)
@SequenceGenerator(name = "TEAM_JOIN_SEQ_GENERATOR", sequenceName = "TEAM_JOIN_SEQ", initialValue = 1, allocationSize = 1)
class TeamJoin(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_JOIN_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", updatable = false, nullable = false)
    val team: Team,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    val user: User,
)
