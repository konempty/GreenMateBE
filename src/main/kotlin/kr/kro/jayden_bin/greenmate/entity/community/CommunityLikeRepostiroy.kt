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

    fun existsByCommunityAndUser(
        community: Community,
        user: User,
    ): Boolean

    fun countByCommunity(community: Community): Int
}
