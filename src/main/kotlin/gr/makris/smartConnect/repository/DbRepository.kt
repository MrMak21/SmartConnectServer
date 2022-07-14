package gr.makris.smartConnect.repository

import gr.makris.smartConnect.data.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface DbRepository: JpaRepository<User,String> {

    @Query("SELECT * FROM Users", nativeQuery = true)
    fun getUsers(): List<User>

    @Transactional
    @Modifying
    @Query("Update users u SET u.enabled = 1 WHERE u.userid = :userid", nativeQuery = true)
    fun enableUser(@Param("userid") userId: String)

    @Transactional
    @Modifying
    @Query("Update users u SET u.password = :password WHERE u.email = :email", nativeQuery = true)
    fun resetUserPassword(@Param("password") password: String, @Param("email") email: String): Int

    override fun <S : User?> save(entity: S): S {
        return entity
    }

    @Query("SELECT * FROM Users u where u.email = :email ", nativeQuery = true)
    fun findUserByEmail(email: String): User

    @Query("SELECT exists(select u.email from users u where u.email = :email) as userExists", nativeQuery = true)
    fun checkIfUserExistsWithEmail(email: String)  : Int
}