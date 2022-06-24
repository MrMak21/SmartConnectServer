package gr.makris.smartConnect.service

import gr.atcom.gpslocationservice.model.common.DataResult
import gr.atcom.gpslocationservice.model.common.DataResultWithError
import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.data.user.User


interface UserService {

    fun getUsers(): List<User>
    fun createUser(user: User): DataResultWithError<User, Model>
}