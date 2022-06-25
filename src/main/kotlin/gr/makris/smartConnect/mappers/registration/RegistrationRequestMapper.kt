package gr.makris.smartConnect.mappers.registration

import gr.makris.smartConnect.data.requests.registration.RegistrationRequest
import gr.makris.smartConnect.data.user.User
import java.util.*

fun RegistrationRequest.toUserModel(): User {
    return User(
        userId = this.userId.ifEmpty { UUID.randomUUID().toString() },
        firstname = this.firstname,
        lastname = this.lastname,
        email = this.email,
        password = this.password,
        enabled = false
    )
}