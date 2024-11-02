package kr.kro.jayden_bin.greenmate.entity.community

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
    name = "communities",
)
@SequenceGenerator(name = "COMMUNITY_SEQ_GENERATOR", sequenceName = "COMMUNITY_SEQ", initialValue = 1, allocationSize = 1)
class Community(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNITY_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    val user: User,
    @Column(length = 50, nullable = false, updatable = false)
    val title: String,
    @Column(nullable = false, columnDefinition = "clob")
    val description: String,
) : BaseTimeEntity()
