package kr.kro.jayden_bin.greenmate.config

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import kr.kro.jayden_bin.greenmate.util.UserArgumentResolver
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.time.format.DateTimeFormatter
import java.util.Locale

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userArgumentResolver())
    }

    @Bean
    fun jsonCustomizer() =
        Jackson2ObjectMapperBuilderCustomizer {
            val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
            it.simpleDateFormat(dateTimeFormat)
            it.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
            it.deserializers(LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
        }

    @Bean
    fun localeResolver() =
        SessionLocaleResolver().apply {
            setDefaultLocale(Locale.KOREA)
        }

    @Bean
    fun userArgumentResolver() = UserArgumentResolver()
}
