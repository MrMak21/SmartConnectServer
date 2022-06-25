package gr.makris.smartConnect.data.requests.registration

data class RegistrationRequest(
    var userId: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String
)
