package gr.makris.smartConnect.controllers.server

import com.google.gson.Gson
import gr.makris.smartConnect.data.server.ServerCheck
import gr.makris.smartConnect.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ServerController {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    private lateinit var userService: UserService

    private lateinit var gson: Gson

    init {
        gson = Gson()
    }

    @GetMapping("/api/smartConnect/server-check")
    fun serverCheck(): String {
        //do some tests -- maybe check if connected to database

        val serverStatus = ServerCheck(serverVersion = env.getProperty("server.version",""))
        return gson.toJson(serverStatus)
    }

    @GetMapping("/api/smartConnect/getUsers")
    fun getUsers(): String {
        val users = userService.getUsers()
        return gson.toJson(users)
    }


}