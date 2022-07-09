package gr.makris.smartConnect.exceptions.userExceptions

import gr.makris.smartConnect.exceptions.base.SmartConnectException

class InvalidCredentialsException(
    override var errorCode: String,
    override var errorMessage: String
) : SmartConnectException() {
}