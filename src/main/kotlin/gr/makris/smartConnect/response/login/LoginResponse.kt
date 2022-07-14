package gr.makris.smartConnect.response.login

import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.response.base.SmartConnectResponse

data class LoginResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
): SmartConnectResponse()
