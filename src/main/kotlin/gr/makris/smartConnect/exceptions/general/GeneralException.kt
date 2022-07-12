package gr.makris.smartConnect.exceptions.general

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class GeneralException(
    override var errorMessage: String = "Something went wrong. Please try again",
    override var errorCode: String = "GEN10"
): SmartConnectException(), Model {
}