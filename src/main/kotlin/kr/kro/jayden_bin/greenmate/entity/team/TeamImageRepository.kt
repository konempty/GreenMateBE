package kr.kro.jayden_bin.greenmate.entity.team

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamImageRepository : JpaRepository<TeamImage, Long> {
    fun findByTeam(community: Team): List<TeamImage>
}
