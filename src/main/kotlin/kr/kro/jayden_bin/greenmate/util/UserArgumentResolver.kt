package kr.kro.jayden_bin.greenmate.util

import kr.kro.jayden_bin.greenmate.entity.user.User
import kr.kro.jayden_bin.greenmate.exceptions.HttpException
import kr.kro.jayden_bin.greenmate.service.SecurityUser
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) = User::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): User {
        val authentication = SecurityContextHolder.getContext().authentication

        return if (authentication.isAuthenticated && authentication.principal is SecurityUser) {
            (authentication.principal as SecurityUser).user
        } else {
            throw HttpException(HttpStatus.UNAUTHORIZED, "인증정보가 없습니다.")
        }
    }
}
