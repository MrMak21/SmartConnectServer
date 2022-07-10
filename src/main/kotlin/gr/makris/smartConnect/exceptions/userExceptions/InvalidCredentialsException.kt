package gr.makris.smartConnect.exceptions.userExceptions

import gr.makris.smartConnect.exceptions.base.SmartConnectException

class InvalidCredentialsException(
    override var errorCode: String = "AUTH20",
    override var errorMessage: String = "Email or password are wrong. Please try again"
) : SmartConnectException() {
}