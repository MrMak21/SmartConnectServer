package gr.makris.smartConnect.controllers.server

import com.google.gson.Gson
import gr.makris.smartConnect.data.server.ServerCheck
import gr.makris.smartConnect.exceptions.userExceptions.UnauthorizedException
import gr.makris.smartConnect.manager.authenticationManager.AuthenticationManager
import gr.makris.smartConnect.response.server.ServerCheckResponse
import gr.makris.smartConnect.response.users.GetUsersResponse
import gr.makris.smartConnect.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController


@RestController
class ServerController {

    @Autowired
    private lateinit var env: Environment

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    private lateinit var gson: Gson

    init {
        gson = Gson()
    }

    @GetMapping("/api/smartConnect/server-check", produces = ["application/json"])
    fun serverCheck(): ResponseEntity<String> {
        //do some tests -- maybe check if connected to database

        val serverStatus = ServerCheck(serverVersion = env.getProperty("server.version",""))
        return ResponseEntity.ok(gson.toJson(
            ServerCheckResponse(serverStatus.serverVersion, serverStatus.serverName, serverStatus.serverStatus)
        ))
    }

    @GetMapping("/api/smartConnect/getUsers", produces= ["application/json"])
    fun getUsers(@RequestHeader(name = "Authorization", required = true) x_auth_token: String): ResponseEntity<String> {
        val isUserAuthenticated = authenticationManager.validateUser(x_auth_token)
        if (!isUserAuthenticated) {
            throw UnauthorizedException()
        }
        val users = userService.getUsers()
        return ResponseEntity(gson.toJson(
            GetUsersResponse(usersList = users)
        ), HttpStatus.OK)
    }


}