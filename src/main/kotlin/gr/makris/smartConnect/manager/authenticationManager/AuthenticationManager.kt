package gr.makris.smartConnect.manager.authenticationManager

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import gr.makris.smartConnect.data.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
class AuthenticationManager {

    @Autowired
    private lateinit var env: Environment

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

            val localDateTime = LocalDateTime.ofInstant(decryptToken.expiresAtAsInstant, ZoneOffset.UTC)

            if (issuer == decryptToken.issuer
//            && user.email == decryptToken.subject
                && localDateTime.isAfter(LocalDateTime.now())
            )
                return true

            return false
        } catch (t: Throwable) {
            return false
        }
    }
}