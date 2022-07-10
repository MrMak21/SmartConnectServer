package gr.makris.smartConnect.controllers.registration

import com.google.gson.Gson
import gr.makris.smartConnect.SmartConnectApplication
import gr.makris.smartConnect.data.requests.registration.RegistrationRequest
import gr.makris.smartConnect.mappers.registration.toUserModel
import gr.makris.smartConnect.security.PasswordEncoder
import gr.makris.smartConnect.service.user.UserService
import gr.makris.smartConnect.service.registration.ConfirmationTokenServiceImpl
import gr.makris.smartConnect.data.registration.ConfirmationToken
import gr.makris.smartConnect.mappers.confirmationToken.toJsonFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class RegisterController {

    val logger: Logger = LoggerFactory.getLogger(RegisterController::class.java)

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var confirmationTokenService: ConfirmationTokenServiceImpl

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private var gson: Gson = Gson()

    @PostMapping("/api/smartConnect/createNewUser")
    fun createNewUser(@RequestBody userRequest: RegistrationRequest): ResponseEntity<String> {
        val user = userRequest.toUserModel()

        val encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.password)
        user.password = encodedPassword

        val response = userService.createUser(user = user)
        val confirmationTokenRequest = ConfirmationToken(
            tokenid = UUID.randomUUID().toString(),
            token = UUID.randomUUID().toString(),
            createdat = LocalDateTime.now(),
            expiresat = LocalDateTime.now().plusMinutes(15),
            userid = user.userId
        )

        val confirmationTokenResponse = confirmationTokenService.saveConfirmationToken(confirmationTokenRequest)

        response.data.let {
            confirmationTokenResponse.data?.let { token ->
                logger.info(token.token)
            }
            return ResponseEntity.ok(gson.toJson(it))
        }
    }

    @GetMapping("/api/smartConnect/confirm", produces = ["application/json"])
    fun confirmToken(@RequestParam token: String): ResponseEntity<String> {
         val response = confirmationTokenService.confirmToken(token)

        response.data.let {
            return ResponseEntity.ok(gson.toJson(it?.toJsonFormat()))
        }
//            ?: response.error.let {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(gson.toJson(it?.message))
//        }
    }
}