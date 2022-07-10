package gr.makris.smartConnect.data.registration

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.apache.catalina.User
import java.time.LocalDateTime
import javax.persistence.*


data class ConfirmationTokenJsonFormat(
    var tokenid: String,
    var userid: String,
    var token: String,
    var createdat: String,
    var expiresat: String,
    var confirmedat: String? = null
)