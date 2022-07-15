package gr.makris.smartConnect.data.server

data class ServerCheck(
    var serverName: String = "SmartConnectServer",
    var serverVersion: String = "1.0.0",
    var serverStatus: String = "OK"
)
