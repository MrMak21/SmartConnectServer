package gr.makris.smartConnect.data.registration

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.apache.catalina.User
import java.time.LocalDateTime
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "confirmationtoken")
data class ConfirmationToken(
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="tokenid")
    var tokenid: String,
    @Column(nullable = false)
    var userid: String,
    @Column(name = "token", nullable = false)
    var token: String,
    @Column(nullable = false)
    var createdat: LocalDateTime,
    @Column(nullable = false)
    var expiresat: LocalDateTime,
    var confirmedat: LocalDateTime? = null
)