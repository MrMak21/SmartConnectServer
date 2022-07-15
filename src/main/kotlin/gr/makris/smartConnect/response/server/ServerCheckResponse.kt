package gr.makris.smartConnect.response.server

import gr.makris.smartConnect.response.base.SmartConnectResponse

class ServerCheckResponse(
    val serverVersion: String,
    val serverName: String,
    val serverStatus: String
): SmartConnectResponse() {
}