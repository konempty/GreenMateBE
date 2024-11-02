package kr.kro.jayden_bin.greenmate.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github")
data class GithubProperties(
    val token: String,
)
