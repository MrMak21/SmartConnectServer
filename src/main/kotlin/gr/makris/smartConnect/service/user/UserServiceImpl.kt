package gr.makris.smartConnect.service.user

import gr.atcom.gpslocationservice.model.common.DataResultWithError
import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.data.user.CreateUserErrorModel
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.data.user.UserNotFoundException
import gr.makris.smartConnect.repository.DbRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userJpaRepository: DbRepository

    override fun getUsers(): List<User> {
        return userJpaRepository.getUsers()
    }

    override fun createUser(user: User): DataResultWithError<User, Model> {
        return try {
            DataResultWithError(userJpaRepository.save(user))
        } catch (t: DataIntegrityViolationException) {
//            DataResult(error = Throwable(message = "A user with this email already exists"))
            DataResultWithError(error = CreateUserErrorModel(message = "A user with this email already exists - " + user.email, "100"))
        } catch (t: Throwable) {
            DataResultWithError(error = CreateUserErrorModel(message = "Something went wrong. Please try again later"))
        }
    }

    override fun getUserByEmail(email: String): DataResultWithError<User, Model> {
        return try {
            DataResultWithError(userJpaRepository.findUserByEmail(email))
        } catch (t: Throwable) {
//            throw UserNotFoundException()
            DataResultWithError(error = UserNotFoundException(errorMessage = "User not found", errorCode = "24"))
        }
    }
}