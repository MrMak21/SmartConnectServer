package gr.makris.smartConnect.exceptions.base

import gr.atcom.gpslocationservice.model.common.Model


open class SmartConnectException(
    open var errorMessage: String = "",
    open var errorCode: String = ""
): RuntimeException(), Model