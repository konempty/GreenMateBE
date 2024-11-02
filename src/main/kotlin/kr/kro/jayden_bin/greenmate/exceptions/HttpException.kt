package kr.kro.jayden_bin.greenmate.exceptions

import org.springframework.http.HttpStatus

open class HttpException(
    val httpStatus: HttpStatus,
    message: String?,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
