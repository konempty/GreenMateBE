package kr.kro.jayden_bin.greenmate.entity.community

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityCommentRepository : JpaRepository<CommunityComment, Long> {
    fun findByCommunity(community: Community): List<CommunityComment>

    fun countByCommunity(community: Community): Int
}
