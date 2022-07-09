package gr.makris.smartConnect.manager.authenticationManager

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.exceptions.userExceptions.UserNotFoundException
import gr.makris.smartConnect.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset

@Component
class AuthenticationManager {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    private lateinit var userService: UserService

    fun createAccessToken(user: User): Pair<String, String> {
        val secret: String = env.getProperty("jwt.secret", "")
        val issuer: String = env.getProperty("jwt.issuer", "")
        val algorithm = Algorithm.HMAC256(secret.toByteArray())

        val accessToken = JWT.create()
            .withIssuer(issuer)
            .withSubject(user.email)
            .withExpiresAt(LocalDateTime.now().plusMinutes(40).toInstant(ZoneOffset.UTC))
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withIssuer(issuer)
            .withSubject(user.email)
            .withExpiresAt(LocalDateTime.now().plusMonths(6).toInstant(ZoneOffset.UTC))
            .sign(algorithm)

        return Pair(accessToken, refreshToken)
    }

    fun validateUser(accessToken: String): Boolean {
        try {
            val decryptToken = JWT().decodeJwt(accessToken)
            val issuer: String = env.getProperty("jwt.issuer", "")

            val tokenExpireTime = LocalDateTime.ofInstant(decryptToken.expiresAtAsInstant, ZoneOffset.UTC)

            if (issuer == decryptToken.issuer
//            && user.email == decryptToken.subject
                && tokenExpireTime.isAfter(LocalDateTime.now())
            )
                return true

            return false
        } catch (t: Throwable) {
            return false
        }
    }

    fun refreshAccessToken(refreshToken: String): Pair<String, String> {
        try {
            val decryptToken = JWT().decodeJwt(refreshToken)
            val issuer: String = env.getProperty("jwt.issuer", "")
            val userEmail = decryptToken.subject

            val userResponse = userService.getUserByEmail(userEmail)

            userResponse.data?.let {
                val tokenExpireTime = LocalDateTime.ofInstant(decryptToken.expiresAtAsInstant, ZoneOffset.UTC)

                if (issuer == decryptToken.issuer
                    && tokenExpireTime.isAfter(LocalDateTime.now())) {
                    return createAccessToken(it)
                } else {
                    throw UserNotFoundException(errorMessage = "Refresh token invalid", errorCode = "TOK11")
                }
            } ?: userResponse.error.let {
                throw UserNotFoundException(errorMessage = "Refresh token invalid", errorCode = "TOK11")
            }
        } catch (t: Throwable) {
            throw UserNotFoundException(errorMessage = "Refresh token invalid", errorCode = "TOK11")
        }
    }
}