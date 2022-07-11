package gr.makris.smartConnect.controllers.login

import com.google.gson.Gson
import gr.makris.smartConnect.SmartConnectApplication
import gr.makris.smartConnect.data.requests.login.LoginUserRequest
import gr.makris.smartConnect.exceptions.userExceptions.UserNotFoundException
import gr.makris.smartConnect.data.user.UserWrongPasswordErrorModel
import gr.makris.smartConnect.exceptions.userExceptions.InvalidCredentialsException
import gr.makris.smartConnect.exceptions.userExceptions.UserNotConfirmedException
import gr.makris.smartConnect.manager.authenticationManager.AuthenticationManager
import gr.makris.smartConnect.response.login.LoginResponse
import gr.makris.smartConnect.security.PasswordEncoder
import gr.makris.smartConnect.service.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpSession
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class LoginController {

    val logger: Logger = LoggerFactory.getLogger(SmartConnectApplication::class.java)

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
                if (!it.enabled) { // if user has not confirmed his acc registration
                    throw UserNotConfirmedException()
                }

                // success login
                val accessTokenPair = authenticationManager.createAccessToken(it)
                val accessToken = accessTokenPair.first
                val refreshToken = accessTokenPair.second

                logger.info("Access token: $accessToken\n" +
                            "Refresh token: $refreshToken")

                return ResponseEntity.status(HttpStatus.OK)
                    .header("accessToken", accessToken)
                    .header("refreshToken", refreshToken)
                    .body(gson.toJson(LoginResponse(it, accessToken, refreshToken)))
            } else {
                throw InvalidCredentialsException()
            }
        } ?: findUserResponse.error.let {
            throw InvalidCredentialsException()
        }
    }

    @GetMapping("api/smartConnect/googleLoginUser")
    fun googleSignIn(@RequestParam(value = "idToken") idTokenString: String) {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(Collections.singletonList("168238449623-dv5soo6a9bck2ap1rhv4jkvcqn1f1i8s.apps.googleusercontent.com"))
            .build()

        try {
            val idToken = verifier.verify(idTokenString)
            if (idToken != null) {
                val payload = idToken.payload

                //get information

            }
        } catch (t: Throwable) {
            t.message
        }

    }

}