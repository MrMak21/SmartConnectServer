package gr.makris.smartConnect.repository

import gr.makris.smartConnect.data.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface DbRepository: JpaRepository<User,String> {

    @Query("SELECT * FROM Users", nativeQuery = true)
    fun getUsers(): List<User>

    override fun <S : User?> save(entity: S): S {
        return entity
    }
}