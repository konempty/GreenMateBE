package kr.kro.jayden_bin.greenmate.config

import kr.kro.jayden_bin.greenmate.config.security.filter.JwtFilter
import kr.kro.jayden_bin.greenmate.config.security.handler.CustomAuthenticationEntryPoint
import kr.kro.jayden_bin.greenmate.service.CustomUserDetailService
import kr.kro.jayden_bin.greenmate.util.jwt.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val jwtUtil: JwtUtil,
    private val customUserDetailService: CustomUserDetailService,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(
                JwtFilter(jwtUtil, customUserDetailService),
                UsernamePasswordAuthenticationFilter::class.java,
            ).authorizeHttpRequests {
                it
                    .requestMatchers(*PERMITTED_URL_PATTERNS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }.build()

    @Bean
    fun bcryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}

val PERMITTED_URL_PATTERNS =
    arrayOf(
        "/health",
        "/ready",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/api/v1/users/**",
    )
