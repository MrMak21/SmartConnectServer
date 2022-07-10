package gr.makris.smartConnect.exceptions.confirmationToken

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class ConfirmationTokenNotFoundException(
    override var errorCode: String,
    override var errorMessage: String
): SmartConnectException(), Model
