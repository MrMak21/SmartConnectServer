package gr.makris.smartConnect.controllers.login

import com.google.gson.Gson
import gr.makris.smartConnect.data.requests.login.LoginUserRequest
import gr.makris.smartConnect.data.user.UserWrongPasswordErrorModel
import gr.makris.smartConnect.security.PasswordEncoder
import gr.makris.smartConnect.service.registration.ConfirmationTokenServiceImpl
import gr.makris.smartConnect.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var gson: Gson = Gson()

    @PostMapping("api/smartConnect/loginUser")
    fun loginUser(@RequestBody userLoginRequest: LoginUserRequest): ResponseEntity<String> {

        val findUserResponse = userService.getUserByEmail(userLoginRequest.email)

        findUserResponse.data?.let {
            val isPasswordRight = passwordEncoder.bCryptPasswordEncoder().matches(userLoginRequest.password, it.password)
            if (isPasswordRight) {
                // success login
                return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(it))
            } else {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson(
                     UserWrongPasswordErrorModel(
                         message = "Email or password are wrong. Please try again",
                         errorCode = "20"
                     )
                 ))
            }
        } ?: findUserResponse.error.let {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson(it))
        }
    }
}