package gr.makris.smartConnect.exceptions.userExceptions

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class UserAlreadyExistsException(
    override var errorMessage: String = "A user with this email already exists",
    override var errorCode: String = "AUTH15"
): SmartConnectException(), Model {
}