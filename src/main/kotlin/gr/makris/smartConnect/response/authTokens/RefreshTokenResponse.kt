package gr.makris.smartConnect.response.authTokens

import gr.makris.smartConnect.response.base.SmartConnectResponse

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
): SmartConnectResponse()
