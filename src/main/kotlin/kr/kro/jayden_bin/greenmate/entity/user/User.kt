package kr.kro.jayden_bin.greenmate.entity.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import kr.kro.jayden_bin.greenmate.entity.BaseTimeEntity

@Entity
@Table(
    name = "users",
)
@SequenceGenerator(name = "USERS_SEQ_GENERATOR", sequenceName = "USERS_SEQ", initialValue = 1, allocationSize = 1)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0L,
    @Column(length = 30, nullable = false, updatable = false, unique = true)
    val nickName: String,
    @Column(length = 50, nullable = false, updatable = false, unique = true)
    val email: String,
    @Column(length = 100, nullable = false)
    val password: String,
    @Column(length = 255, nullable = false)
    val profileImageName: String,
) : BaseTimeEntity()
