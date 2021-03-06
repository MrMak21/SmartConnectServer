package gr.makris.smartConnect.controllers.authorization

import com.google.gson.Gson
import gr.makris.smartConnect.SmartConnectApplication
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.manager.authenticationManager.AuthenticationManager
import gr.makris.smartConnect.response.authTokens.RefreshTokenResponse
import gr.makris.smartConnect.service.email.GmailServiceProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorizationController {

    val logger: Logger = LoggerFactory.getLogger(AuthorizationController::class.java)

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var emailService: GmailServiceProvider

    private var gson: Gson = Gson()

    @GetMapping("/api/smartConnect/refreshAccessToken", produces = ["application/json"])
    fun refreshAccessToken(
        @RequestParam(
            name = "refreshToken",
            required = true
        ) refreshToken: String
    ): ResponseEntity<String> {

        val tokensGenerated = authenticationManager.refreshAccessToken(refreshToken)
        return ResponseEntity(
            gson.toJson(
                RefreshTokenResponse(
                    tokensGenerated.first, tokensGenerated.second
                )
            ), HttpStatus.OK
        )
    }

    @GetMapping("/api/smartConnect/sendMail", produces = ["application/json"])
    fun sendEmail() {
        emailService.createEmail(User("", "panos", "mak", "mak@gmail.com", "12345", true),
        "12342341234124213421343214")
    }
}