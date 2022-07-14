package gr.makris.smartConnect.response.base

import java.time.LocalDateTime
import java.time.ZoneOffset

open class SmartConnectResponse(
    open val timestamp: String = LocalDateTime.now().toInstant(ZoneOffset.UTC).toString()
)
