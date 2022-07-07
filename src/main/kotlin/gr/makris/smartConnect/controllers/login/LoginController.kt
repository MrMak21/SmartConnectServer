package gr.makris.smartConnect.controllers.login

import com.google.gson.Gson
import gr.makris.smartConnect.data.requests.login.LoginUserRequest
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.data.user.UserWrongPasswordErrorModel
import gr.makris.smartConnect.response.login.LoginResponse
import gr.makris.smartConnect.security.PasswordEncoder
import gr.makris.smartConnect.service.registration.ConfirmationTokenServiceImpl
import gr.makris.smartConnect.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.Base64
import javax.servlet.http.HttpSession

@RestController
class LoginController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var gson: Gson = Gson()

    @PostMapping("api/smartConnect/loginUser")
    fun loginUser(@RequestBody userLoginRequest: LoginUserRequest, httpSession: HttpSession): ResponseEntity<String> {

        val findUserResponse = userService.getUserByEmail(userLoginRequest.email)

        findUserResponse.data?.let {
            val isPasswordRight = passwordEncoder.bCryptPasswordEncoder().matches(userLoginRequest.password, it.password)
            if (isPasswordRight) {
                // success login
                val x_auth_token = getAccessToken(it)
                return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(LoginResponse(it,x_auth_token)))
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

    private fun getAccessToken(user: User): String {
        val stringToEncode = "${user.email} ${user.firstname} ${user.lastname}"
        val accessToken = Base64.getEncoder().encodeToString(stringToEncode.toByteArray())
        return accessToken
    }
}