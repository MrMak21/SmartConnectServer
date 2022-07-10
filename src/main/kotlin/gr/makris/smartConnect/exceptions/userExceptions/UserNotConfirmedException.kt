package gr.makris.smartConnect.exceptions.userExceptions

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class UserNotConfirmedException(
    override var errorCode: String = "CONF33",
    override var errorMessage: String = "You have not confirmed your account. Please request a new confirmation token"
): SmartConnectException(), Model {
}