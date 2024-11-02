package kr.kro.jayden_bin.greenmate.entity.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByNickname(nickname: String): Boolean

    fun findByEmail(email: String): User?
}
