package kr.kro.jayden_bin.greenmate.entity.community

import kr.kro.jayden_bin.greenmate.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityLikeRepository : JpaRepository<CommunityLike, Long> {
    fun findByCommunityAndUser(
        community: Community,
        user: User,
    ): CommunityLike?

    fun countByCommunity(community: Community): Int
}
