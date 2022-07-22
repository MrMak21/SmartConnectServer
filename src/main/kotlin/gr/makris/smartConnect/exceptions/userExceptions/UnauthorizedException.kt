package gr.makris.smartConnect.exceptions.userExceptions

import gr.makris.smartConnect.exceptions.base.SmartConnectException

class UnauthorizedException(
    override var errorCode: String = "AUTH30",
    override var errorMessage: String = "Unauthorized"
) : SmartConnectException() {
}