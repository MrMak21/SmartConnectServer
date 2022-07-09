package gr.makris.smartConnect.exceptions.userExceptions

import gr.atcom.gpslocationservice.model.common.Model

class UserNotFoundException(
    var errorMessage: String = "",
    var errorCode: String = ""
) : RuntimeException(), Model