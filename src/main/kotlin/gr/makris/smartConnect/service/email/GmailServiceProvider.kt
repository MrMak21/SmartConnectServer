package gr.makris.smartConnect.service.email

import com.google.gson.Gson
import gr.makris.smartConnect.data.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


@Component
class GmailServiceProvider {

    val applicationName: String = "SmartConnect"
    private var gson: Gson = Gson()
    private val emailFrom = "smartconnectgr@gmail.com"
    private val serverAddress: String = "http://localhost:7777/api/smartConnect/confirm"

    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun createEmail(user: User, token: String) {
        val props = Properties()
        props.put("mail.debug", "true");
        val session = Session.getDefaultInstance(props, null)

        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress(emailFrom))
        mimeMessage.setRecipient(Message.RecipientType.TO,InternetAddress(user.email))
        mimeMessage.setSubject("Welcome to Smart Connect!")
        mimeMessage.setContent(
            "<html>" +
                    "   <head>\n" +
                    "   <style>\n" +
                    "   </style>\n" +
                    "   </head>\n" +
                    "   <body>\n" +
                    "<h1 style=\"color: #5e9ca0;\">Welcome to Smart Connect!</h1>\n" +
                    "<h2 style=\"color: #2e6c80;\">We are glad to have you on board!</h2>\n" +
                    "<h2 style=\"color: #2e6c80;\">Hello " + user.firstname.capitalize() +"</h2>\n" +
                    "<p style=\"color:black;\">In order to complete your registration you have to verify your email address. Click the above button so we can continue.&nbsp;</p>\n" +
                    "<p style=\"color:black;\">Click here to <a href=\"" + serverAddress + "?token=" + token + "\" style=\"background-color: #0000ff; color: #fff; display: inline-block; padding: 3px 10px; font-weight: bold; border-radius: 5px;\">VERIFY</a>&nbsp;your account.</p>\n" +
                    "<p style=\"color:black;\"><strong>After this step you can use Smart Connect without restrictions.</strong><br /><strong>Enjoy!</strong></p>\n" +
                    "<p style=\"color:black;\"><strong>&nbsp;</strong></p>\n" +
                    "  </body>\n" +
                    "</html>"
            , "text/html")


//        val email = SimpleMailMessage()
//        email.setFrom(emailFrom)
//        email.setTo(emailTo)
//        email.setSubject("Welcome to Smart Connect!")
//        email.setText(
//                "<h1 style=\"color: #5e9ca0;\">Welcome to Smart Connect!</h1>\n" +
//                "<h2 style=\"color: #2e6c80;\">We are glad to have you on board!</h2>\n" +
//                "<h2 style=\"color: #2e6c80;\">Hello " + user.firstname +"</h2>\n" +
//                "<p>In order to complete your registration you have to verify your email address. Click the above button so we can continue.&nbsp;</p>\n" +
//                "<p>Click here to <span style=\"background-color: #0000ff; color: #fff; display: inline-block; padding: 3px 10px; font-weight: bold; border-radius: 5px;\" href=\"" + serverAddress + "?token=" + token +">VERIFY</span>&nbsp;your account.</p>\n" +
//                "<p><strong>After this step you can use Smart Connect with no restrictions.</strong><br /><strong>Enjoy!</strong></p>\n" +
//                "<p><strong>&nbsp;</strong></p>")

//        sendEmail(email)
        sendEmail(mimeMessage)
    }

    fun sendEmail(email: SimpleMailMessage) {
        try {
            mailSender.send(email)
        } catch (t:  Throwable) {
            t.message
        }
     }

    fun sendEmail(email: MimeMessage) {
        try {
            mailSender.send(email)
        } catch (t:  Throwable) {
            t.message
        }
    }

}