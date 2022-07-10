package gr.makris.smartConnect.mappers.confirmationToken

import gr.makris.smartConnect.data.registration.ConfirmationToken
import gr.makris.smartConnect.data.registration.ConfirmationTokenJsonFormat

fun ConfirmationToken.toJsonFormat(): ConfirmationTokenJsonFormat {
    return ConfirmationTokenJsonFormat(
        this.tokenid,
        this.userid,
        this.token,
        this.createdat.toString(),
        this.expiresat.toString(),
        this.confirmedat.toString()
    )
}