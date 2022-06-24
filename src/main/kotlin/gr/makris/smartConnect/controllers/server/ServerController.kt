package gr.makris.smartConnect.controllers.server

import com.google.gson.Gson
import gr.makris.smartConnect.data.server.ServerCheck
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.repository.DbRepository
import gr.makris.smartConnect.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


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

    @GetMapping("/server-check")
    fun serverCheck(): String {
        //do some tests -- maybe check if connected to database

        val serverStatus = ServerCheck(serverVersion = env.getProperty("server.version",""))
        return gson.toJson(serverStatus)
    }

    @PostMapping("/createNewUser")
    fun createNewUser(@RequestBody user: User): ResponseEntity<String> {

        val response = userService.createUser(user = User(UUID.randomUUID().toString(), user.firstname, user.lastname, user.email, user.password))

        response.data?.let {
            return ResponseEntity.ok(gson.toJson(it))
//            return gson.toJson(it)
        } ?: response.error.let {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(gson.toJson(it))
        }

    }

    @GetMapping("/getUsers")
    fun getUsers(): String {
        val users = userService.getUsers()
        return gson.toJson(users)
    }


}