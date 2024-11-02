package kr.kro.jayden_bin.greenmate.entity.community

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityImageRepository : JpaRepository<CommunityImage, Long> {
    fun findByCommunity(community: Community): List<CommunityImage>
}
