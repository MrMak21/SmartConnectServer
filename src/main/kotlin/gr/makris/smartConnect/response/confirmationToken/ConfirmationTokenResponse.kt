package gr.makris.smartConnect.response.confirmationToken

import gr.makris.smartConnect.data.registration.ConfirmationTokenJsonFormat
import gr.makris.smartConnect.response.base.SmartConnectResponse

data class ConfirmationTokenResponse(
    val confirmationToken: ConfirmationTokenJsonFormat
): SmartConnectResponse()
