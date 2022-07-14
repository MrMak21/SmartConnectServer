package gr.makris.smartConnect.data.requests.passwordReset

import gr.atcom.gpslocationservice.model.common.Model

data class PasswordResetRequest(
    val email: String,
    val newPassword: String
): Model {
}