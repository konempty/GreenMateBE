package kr.kro.jayden_bin.greenmate.controller

import jakarta.validation.ConstraintViolationException
import kr.kro.jayden_bin.greenmate.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(
        IllegalArgumentException::class,
        IllegalStateException::class,
        ConstraintViolationException::class,
    )
    fun invalidRequestException(ex: RuntimeException): ResponseEntity<Any>? = getInvalidRequestResponse(ex.message)

    private fun getInvalidRequestResponse(
        errorMessage: String?,
        httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    ): ResponseEntity<Any> {
        val invalidRequestErrorCode = 400
        return ResponseEntity
            .status(httpStatus)
            .body(ErrorResponse(invalidRequestErrorCode, errorMessage ?: "유효하지 않은 요청입니다"))
    }
}
