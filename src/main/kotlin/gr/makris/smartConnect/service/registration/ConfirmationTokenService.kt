package gr.makris.smartConnect.service.registration

import gr.atcom.gpslocationservice.model.common.DataResult
import gr.makris.smartConnect.tokens.registration.ConfirmationToken

interface ConfirmationTokenService {

    fun saveConfirmationToken(token: ConfirmationToken): DataResult<ConfirmationToken, Throwable>
    fun confirmToken(token: String): DataResult<ConfirmationToken, Throwable>
}