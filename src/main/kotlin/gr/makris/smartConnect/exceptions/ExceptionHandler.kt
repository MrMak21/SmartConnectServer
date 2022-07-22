package gr.makris.smartConnect.exceptions

import gr.makris.smartConnect.data.error.ApiError
import gr.makris.smartConnect.data.error.CustomError
import gr.makris.smartConnect.exceptions.confirmationToken.ConfirmationExpiredTokenException
import gr.makris.smartConnect.exceptions.confirmationToken.ConfirmationTokenNotFoundException
import gr.makris.smartConnect.exceptions.general.GeneralException
import gr.makris.smartConnect.exceptions.login.GoogleLoginException
import gr.makris.smartConnect.exceptions.userExceptions.*
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.time.ZoneOffset


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [UserNotFoundException::class])
    fun userNotFoundException(ex: UserNotFoundException): ResponseEntity<Any?> {
        return buildResponseEntity(
            ApiError(HttpStatus.EXPECTATION_FAILED,
                LocalDateTime.now().toInstant(ZoneOffset.UTC).toString(),
                listOf(CustomError(
                    ex.errorCode,
                    ex.errorMessage))
                ))
    }

    @ExceptionHandler(value = [InvalidCredentialsException::class])
    fun invalidUserCredentialsHandler(ex: InvalidCredentialsException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
                listOf(CustomError(ex.errorCode, ex.errorMessage))
            )
    }

    @ExceptionHandler(value = [ConfirmationTokenNotFoundException::class])
    fun confirmationTokenNotFoundExceptionHandler(ex: ConfirmationTokenNotFoundException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [ConfirmationExpiredTokenException::class])
    fun confirmationExpiredTokenExceptionHandler(ex: ConfirmationExpiredTokenException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun userAlreadyExistsHandler(ex: UserAlreadyExistsException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [UserEmailInvalidFormatException::class])
    fun emailInvalidFormatHandler(ex: UserEmailInvalidFormatException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [GoogleLoginException::class])
    fun googleLoginExceptionHandler(ex: GoogleLoginException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [GeneralException::class])
    fun generalExceptionHandler(ex: GeneralException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.NOT_ACCEPTABLE,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorizedExceptionHandler(ex: UnauthorizedException): ResponseEntity<Any?> {
        return buildResponseEntity(
            HttpStatus.UNAUTHORIZED,
            listOf(CustomError(ex.errorCode, ex.errorMessage))
        )
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any?> {
        return ResponseEntity(apiError, apiError.status)
    }

    private fun buildResponseEntity(httpStatus: HttpStatus, errors: List<CustomError>): ResponseEntity<Any?> {
        val apiError = ApiError(
            httpStatus,
            LocalDateTime.now().toInstant(ZoneOffset.UTC).toString(),
            errors
        )
        return ResponseEntity(apiError, httpStatus)
    }


}