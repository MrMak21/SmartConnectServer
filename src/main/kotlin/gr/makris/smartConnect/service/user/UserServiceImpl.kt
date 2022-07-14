package gr.makris.smartConnect.service.user

import gr.atcom.gpslocationservice.model.common.DataResult
import gr.atcom.gpslocationservice.model.common.DataResultWithError
import gr.atcom.gpslocationservice.model.common.Model
import gr.makris.smartConnect.data.user.CreateUserErrorModel
import gr.makris.smartConnect.data.user.GoogleUser
import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.exceptions.userExceptions.UserNotFoundException
import gr.makris.smartConnect.mappers.registration.toRegularUser
import gr.makris.smartConnect.repository.DbRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service
class UserServiceImpl : UserService {

    private val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

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

    override fun createUserFromGoogle(googleUser: GoogleUser): DataResultWithError<User, Model> {
        val user = googleUser.toRegularUser()
        return try {
            DataResultWithError(userJpaRepository.save(user))
        } catch (t: Throwable) {
            DataResultWithError(error = CreateUserErrorModel(message = "Something went wrong. Please try again later"))
        }
    }

    override fun getUserByEmail(email: String): DataResultWithError<User, Model> {
        return try {
            DataResultWithError(userJpaRepository.findUserByEmail(email))
        } catch (t: Throwable) {
            DataResultWithError(error = UserNotFoundException(errorMessage = "User not found", errorCode = "24"))
        }
    }

    override fun checkIfUserExists(email: String): Boolean {
        return try {
            val userExists = userJpaRepository.checkIfUserExistsWithEmail(email)
            userExists >= 1
        } catch (t: Throwable) {
            return true
        }
    }

    override fun checkEmailFormat(email: String): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find()
    }

    override fun passwordReset(email: String, newPassword: String): DataResult<Int, Throwable> {
        return try {
            DataResult(userJpaRepository.resetUserPassword(newPassword, email))
        } catch (t: Throwable) {
            DataResult(error = t)
        }
    }
}