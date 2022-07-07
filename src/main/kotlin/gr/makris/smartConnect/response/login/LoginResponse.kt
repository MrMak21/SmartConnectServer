package gr.makris.smartConnect.response.login

import gr.makris.smartConnect.data.user.User

data class LoginResponse(
    val user: User,
    val x_auth_token: String
)
