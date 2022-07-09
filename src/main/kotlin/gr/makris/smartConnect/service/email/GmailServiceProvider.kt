package gr.makris.smartConnect.service.email

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*


@Component
class GmailServiceProvider {

    val applicationName: String = "SmartConnect"
    private var gson: Gson = Gson()

    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun createEmail(): SimpleMailMessage {

        val props = Properties()
        props.put("mail.debug", "true");
//        val session = Session.getDefaultInstance(props, null)

        val email = SimpleMailMessage()
        email.setFrom("smartconnectgr@gmail.com")
        email.setTo("panosmak37@gmail.com")
        email.setSubject("Smart connect application test email")
        email.setText("Test smart connect server gmail api")

        return email
    }

    fun sendEmail() {
        val email = createEmail()
        try {
            mailSender.send(email)
        } catch (t:  Throwable) {
            t.message
        }
     }

}