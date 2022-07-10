package gr.makris.smartConnect.controllers.registration

import com.google.gson.Gson
import gr.makris.smartConnect.SmartConnectApplication
import gr.makris.smartConnect.data.requests.registration.RegistrationRequest
import gr.makris.smartConnect.mappers.registration.toUserModel
import gr.makris.smartConnect.security.PasswordEncoder
import gr.makris.smartConnect.service.user.UserService
import gr.makris.smartConnect.service.registration.ConfirmationTokenServiceImpl
import gr.makris.smartConnect.data.registration.ConfirmationToken
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.exceptions.userExceptions.UserNotFoundException
import gr.makris.smartConnect.mappers.confirmationToken.toJsonFormat
import gr.makris.smartConnect.service.email.GmailServiceProvider
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

    @Autowired
    private lateinit var emailService: GmailServiceProvider

    private var gson: Gson = Gson()

    @PostMapping("/api/smartConnect/createNewUser", produces = ["application/json"])
    fun createNewUser(@RequestBody userRequest: RegistrationRequest): ResponseEntity<String> {
        val user = userRequest.toUserModel()

        val encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.password)
        user.password = encodedPassword

        val userSaveResponse = userService.createUser(user = user)

        val confirmationTokenRequest = ConfirmationToken(
            tokenid = UUID.randomUUID().toString(),
            token = UUID.randomUUID().toString(),
            createdat = LocalDateTime.now(),
            expiresat = LocalDateTime.now().plusMinutes(15),
            userid = user.userId
        )

        val confirmationTokenResponse = confirmationTokenService.saveConfirmationToken(confirmationTokenRequest)

        userSaveResponse.data.let {
            confirmationTokenResponse.data?.let { token ->
                logger.info(token.token)
                sendEmail(it!!, token.token)
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
    }

    @GetMapping("/api/smartConnect/newConfirmationToken", produces = ["application/json"])
    fun produceNewConfirmationToken(@RequestParam(value = "userEmail") userEmail: String): ResponseEntity<String> {
        val findUserResponse = userService.getUserByEmail(userEmail)

        findUserResponse.data?.let { user ->
            val confirmationTokenRequest = ConfirmationToken(
                tokenid = UUID.randomUUID().toString(),
                token = UUID.randomUUID().toString(),
                createdat = LocalDateTime.now(),
                expiresat = LocalDateTime.now().plusMinutes(15),
                userid = user.userId
            )

            val confirmationTokenResponse = confirmationTokenService.saveConfirmationToken(confirmationTokenRequest)
            confirmationTokenResponse.data?.let {
                sendEmail(user, it.token)
                return ResponseEntity(gson.toJson(it.toJsonFormat()), HttpStatus.OK)
            }

        } ?: findUserResponse.error.let {
            throw UserNotFoundException()
        }
    }

    private fun sendEmail(user: User, token: String) {
        emailService.createEmail(user, token)
    }
}