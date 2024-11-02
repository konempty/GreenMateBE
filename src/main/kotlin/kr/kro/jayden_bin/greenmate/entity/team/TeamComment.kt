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
import kr.kro.jayden_bin.greenmate.entity.user.User

@Entity
@Table(
    name = "team_comments",
)
@SequenceGenerator(name = "TEAM_COMMENT_SEQ_GENERATOR", sequenceName = "TEAM_COMMENT_SEQ", initialValue = 1, allocationSize = 1)
class TeamComment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_COMMENT_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", updatable = false)
    val team: Team,
    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
) : BaseTimeEntity()
