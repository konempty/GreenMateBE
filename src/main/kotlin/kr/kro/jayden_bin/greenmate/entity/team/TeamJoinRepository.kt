package kr.kro.jayden_bin.greenmate.entity.team

import kr.kro.jayden_bin.greenmate.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamJoinRepository : JpaRepository<TeamJoin, Long> {
    fun findByTeamAndUser(
        team: Team,
        user: User,
    ): TeamJoin?

    fun existsByTeamAndUser(
        team: Team,
        user: User,
    ): Boolean

    fun countByTeam(community: Team): Int
}
