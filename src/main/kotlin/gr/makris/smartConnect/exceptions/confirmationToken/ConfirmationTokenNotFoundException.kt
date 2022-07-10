package gr.makris.smartConnect.exceptions.confirmationToken

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class ConfirmationTokenNotFoundException(
    override var errorCode: String = "CONF10",
    override var errorMessage: String = "Confirmation token not found"
): SmartConnectException(), Model
