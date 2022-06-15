package gr.makris.smartConnect.controllers.server

import com.google.gson.Gson
import gr.makris.smartConnect.data.server.ServerCheck
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ServerController {

    @Autowired
    private lateinit var env: Environment

    private lateinit var gson: Gson

    init {
        gson = Gson()
    }

    @GetMapping("/server-check")
    fun serverCheck(): String {
        //do some tests -- maybe check if connected to database

        val serverStatus = ServerCheck(serverVersion = env.getProperty("server.version",""))
        return gson.toJson(serverStatus)
    }
}