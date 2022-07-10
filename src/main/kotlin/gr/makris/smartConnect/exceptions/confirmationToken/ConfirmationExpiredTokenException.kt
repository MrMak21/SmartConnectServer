package gr.makris.smartConnect.exceptions.confirmationToken

import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.exceptions.base.SmartConnectException

class ConfirmationExpiredTokenException(
    override var errorCode: String = "CONF20",
    override var errorMessage: String = "Confirmation token has expired"
): SmartConnectException(), Model
