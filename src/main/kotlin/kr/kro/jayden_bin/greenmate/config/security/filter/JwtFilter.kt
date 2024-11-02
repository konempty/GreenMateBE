package kr.kro.jayden_bin.greenmate.config.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.kro.jayden_bin.greenmate.service.CustomUserDetailService
import kr.kro.jayden_bin.greenmate.util.jwt.JwtUtil
import kr.kro.jayden_bin.greenmate.util.jwt.isBearerToken
import kr.kro.jayden_bin.greenmate.util.jwt.removeBearer
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val customUserDetailService: CustomUserDetailService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val bearerToken = request.getHeader(AUTHORIZATION)
        if (bearerToken.isNullOrBlank() || !bearerToken.isBearerToken()) {
            filterChain.doFilter(request, response)
            return
        }

        val token = bearerToken.removeBearer()
        if (jwtUtil.validateToken(jwtUtil.accessKey, token)) {
            val userIdStr = jwtUtil.getUserId(jwtUtil.accessKey, token).toString()
            val securityUser = customUserDetailService.loadUserByUsername(userIdStr)
            val authentication = UsernamePasswordAuthenticationToken(securityUser, null, securityUser.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
