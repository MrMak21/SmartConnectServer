package gr.makris.smartConnect.exceptions.userExceptions

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class UserEmailInvalidFormatException(
    override var errorMessage: String = "Email format is not acceptable. Please add another email",
    override var errorCode: String = "AUTH16"
): SmartConnectException(), Model {
}