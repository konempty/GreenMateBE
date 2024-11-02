package kr.kro.jayden_bin.greenmate.util.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kr.kro.jayden_bin.greenmate.entity.user.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit.MILLIS
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.crypto.SecretKey

@Component
class JwtUtil(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: StringRedisTemplate,
) {
    val accessKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.accessKey))
    val refreshKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.refreshKey))
    val accessTokenExpirationTime: Long = jwtProperties.accessTokenExpirationTime
    val refreshTokenExpirationTime: Long = jwtProperties.refreshTokenExpirationTime

    val log: Logger = LoggerFactory.getLogger(JwtUtil::class.java)

    fun generateAccessToken(
        user: User,
        profileImageUrl: String,
        expirationTime: Long = accessTokenExpirationTime,
    ): String =
        generateJwtToken(
            mapOf(
                "email" to user.email,
                "userId" to user.id.toString(),
                "nickname" to user.nickname,
                "profileImageUrl" to profileImageUrl,
            ),
            expirationTime,
            accessKey,
        )

    fun generateRefreshToken(
        userId: Long,
        rotateId: String,
        expirationTime: Long = refreshTokenExpirationTime,
    ): String =
        generateJwtToken(
            mapOf(
                "userId" to userId.toString(),
                "rotateId" to rotateId,
            ),
            expirationTime,
            refreshKey,
        )

    fun storeCachedRefreshTokenRotateId(
        userId: Long,
        rotateId: String,
    ) {
        redisTemplate.opsForValue().set(
            getRefreshTokenCacheKey(userId),
            rotateId,
        )
        redisTemplate.expire(getRefreshTokenCacheKey(userId), jwtProperties.refreshTokenExpirationTime, TimeUnit.MILLISECONDS)
    }

    companion object {
        private const val REFRESH_TOKEN_CACHE_PREFIX = "green-mate:refresh-token-cache:"

        fun getRefreshTokenCacheKey(userId: Long): String = REFRESH_TOKEN_CACHE_PREFIX.plus(userId)
    }

    private fun generateJwtToken(
        claims: Map<String, String>,
        expirationTime: Long,
        key: SecretKey,
    ): String =
        Jwts
            .builder()
            .claims(claims)
            .expiration(afterMillis(expirationTime))
            .signWith(key)
            .compact()

    private fun afterMillis(ms: Long): Date =
        Date.from(
            ZonedDateTime.now().plus(ms, MILLIS).toInstant(),
        )

    fun generateRotateId(): String = UUID.randomUUID().toString()

    fun validateCachedRefreshTokenRotateId(token: String): Boolean {
        if (!redisTemplate.hasKey(getRefreshTokenCacheKey(getUserId(refreshKey, token)))) {
            return false
        }
        val cachedRotateId = redisTemplate.opsForValue().get(getRefreshTokenCacheKey(getUserId(refreshKey, token)))
        val rotateIdInToken = getRotateId(token)
        return cachedRotateId == rotateIdInToken
    }

    fun getRotateId(token: String): String = parseClaims(refreshKey, token).get("rotateId", String::class.java)

    fun getUserId(
        key: SecretKey,
        token: String,
    ): Long = parseClaims(key, token).get("userId", String::class.java).toLong()

    fun validateToken(
        key: SecretKey,
        token: String,
    ): Boolean {
        try {
            val expirationTime = parseClaims(key, token).expiration
            return expirationTime.after(Date.from(ZonedDateTime.now().toInstant()))
        } catch (ex: JwtException) {
            log.error("Jwt Exception: $token")
        } catch (ex: IllegalArgumentException) {
            log.error("Invalid Auth Token: $token")
        }
        return false
    }

    private fun parseClaims(
        key: SecretKey,
        token: String,
    ) = Jwts
        .parser()
        .verifyWith(key)
        .decryptWith(key)
        .build()
        .parseSignedClaims(token)
        .payload

    fun removeCachedRefreshToken(userId: Long) {
        redisTemplate.delete(getRefreshTokenCacheKey(userId))
    }
}

fun String.isBearerToken(): Boolean = this.startsWith("Bearer ")

fun String.removeBearer(): String = this.removePrefix("Bearer ")
