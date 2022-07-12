package gr.makris.smartConnect.data.user

data class GoogleUser(
    val firstname: String,
    val lastname: String,
    val email: String,
    val provider: String = "Google"
)
