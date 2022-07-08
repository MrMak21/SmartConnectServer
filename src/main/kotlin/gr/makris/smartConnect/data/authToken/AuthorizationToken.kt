package gr.makris.smartConnect.data.authToken

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "authorizationtoken")
data class AuthorizationToken(
    @Id
    @Column(name = "accesstokenid", nullable = false)
    val accesstokenid: String,

    @Column(name = "userid")
    val userId: String,

    @Column(name = "useremail")
    val userEmail: String,

    @Column(name = "createdat")
    val createdAt: LocalDateTime,

    @Column(name = "expiresat")
    val expiresAt: LocalDateTime,

    @Column(name = "tokenvalue")
    val tokenValue: String,

    @Column(name = "tokentype")
    val tokenType: Enum<AuthTokenTypeEnum>
)
