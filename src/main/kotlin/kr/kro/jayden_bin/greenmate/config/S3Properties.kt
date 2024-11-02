package kr.kro.jayden_bin.greenmate.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
data class S3Properties(
    val bucket: String,
)
