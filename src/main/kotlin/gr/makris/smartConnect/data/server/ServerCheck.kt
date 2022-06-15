package gr.makris.smartConnect.data.server

data class ServerCheck(
    private var serverName: String = "SmartConnectServer",
    private var serverVersion: String = "1.0.0",
    private var serverStatus: String = "OK"
)
