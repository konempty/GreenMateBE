package kr.kro.jayden_bin.greenmate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan
class GreenMateApplication

fun main(args: Array<String>) {
    runApplication<GreenMateApplication>(*args)
}
