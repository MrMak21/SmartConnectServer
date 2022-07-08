package gr.makris.smartConnect.exceptions

import gr.makris.smartConnect.data.user.UserNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {


    @ExceptionHandler(value = [UserNotFoundException::class])
    fun userNotFoundException(ex: UserNotFoundException) {

    }


}