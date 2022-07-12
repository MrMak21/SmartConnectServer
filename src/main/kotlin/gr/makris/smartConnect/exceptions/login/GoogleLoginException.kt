package gr.makris.smartConnect.exceptions.login

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class GoogleLoginException(
    override var errorMessage: String = "Something went wrong with Google Sign-in. Please try a different way",
    override var errorCode: String = "AUTH25"
): SmartConnectException(), Model {
}