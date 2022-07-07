package gr.makris.smartConnect.manager.authenticationManager

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import gr.makris.smartConnect.data.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthenticationManager {

    @Autowired
    private lateinit var env: Environment


    fun createAccessToken(user: User): Pair<String, String> {

        val secret: String = env.getProperty("jwt.secret", "")
        val algorithm = Algorithm.HMAC256(secret.toByteArray())

        val accessToken = JWT.create()
            .withSubject(user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 30000 * 60 * 1000))
            .sign(algorithm)

        return Pair(accessToken, refreshToken)
    }
}