package gr.makris.smartConnect.response.register

import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.response.base.SmartConnectResponse


data class RegisterUserResponse(
    val user: User,
    val message: String = "User created successfully",
    val confirmationToken: String
): SmartConnectResponse()
