package gr.makris.smartConnect.controllers.login

import com.google.gson.Gson
import gr.makris.smartConnect.data.requests.login.LoginUserRequest
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.data.user.UserWrongPasswordErrorModel
import gr.makris.smartConnect.manager.authenticationManager.AuthenticationManager
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
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class LoginController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    private var gson: Gson = Gson()

    @PostMapping("api/smartConnect/loginUser", produces= ["application/json"])
    fun loginUser(@RequestBody userLoginRequest: LoginUserRequest, httpSession: HttpSession): ResponseEntity<String> {

        val findUserResponse = userService.getUserByEmail(userLoginRequest.email)

        findUserResponse.data?.let {
            val isPasswordRight = passwordEncoder.bCryptPasswordEncoder().matches(userLoginRequest.password, it.password)
            if (isPasswordRight) {
                // success login

                val accessTokenPair = authenticationManager.createAccessToken(it)
                val accessToken = accessTokenPair.first
                val refreshToken = accessTokenPair.second

                return ResponseEntity.status(HttpStatus.OK)
                    .header("accessToken", accessToken)
                    .header("refreshToken", refreshToken)
                    .body(gson.toJson(LoginResponse(it, accessToken, refreshToken)))
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