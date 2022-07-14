package gr.makris.smartConnect.response.password

import gr.makris.smartConnect.response.base.SmartConnectResponse

class ResetPasswordResponse(
    val email: String,
    val success: Boolean,
    val message: String
): SmartConnectResponse()
