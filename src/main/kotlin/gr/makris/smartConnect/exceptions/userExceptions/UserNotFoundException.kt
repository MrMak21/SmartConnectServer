package gr.makris.smartConnect.exceptions.userExceptions

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class UserNotFoundException(
    override var errorCode: String = "AUTH10",
    override var errorMessage: String = "We did not found a user with these credentials. Please try again"
) : SmartConnectException(), Model