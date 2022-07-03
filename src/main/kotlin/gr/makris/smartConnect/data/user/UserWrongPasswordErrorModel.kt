package gr.makris.smartConnect.data.user

import gr.atcom.gpslocationservice.model.common.Model

data class UserWrongPasswordErrorModel(
    var message: String = "",
    var errorCode: String = ""
) : Model
