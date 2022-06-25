package gr.makris.smartConnect.repository.registration

import gr.makris.smartConnect.tokens.registration.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Repository
interface ConfirmationTokenRepository: JpaRepository<ConfirmationToken, String> {

    @Query("Select * from confirmationtoken ct where ct.token = :token", nativeQuery = true)
    fun findByToken(@Param("token") token: String): ConfirmationToken

    @Transactional
    @Modifying
    @Query("Update confirmationtoken ct SET ct.confirmedat = :confirmdate WHERE ct.token = :token", nativeQuery = true)
    fun setConfirmedAt(@Param("token") token: String, @Param("confirmdate") confirmedDate: LocalDateTime)

    override fun <S : ConfirmationToken?> save(entity: S): S {
        return entity
    }
}