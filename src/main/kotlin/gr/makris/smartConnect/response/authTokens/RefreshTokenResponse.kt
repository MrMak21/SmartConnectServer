package gr.makris.smartConnect.response.authTokens

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
