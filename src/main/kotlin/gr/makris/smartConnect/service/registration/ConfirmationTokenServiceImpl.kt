package gr.makris.smartConnect.service.registration

import gr.atcom.gpslocationservice.model.common.DataResult
import gr.makris.smartConnect.repository.DbRepository
import gr.makris.smartConnect.repository.registration.ConfirmationTokenRepository
import gr.makris.smartConnect.data.registration.ConfirmationToken
import gr.makris.smartConnect.exceptions.confirmationToken.ConfirmationExpiredTokenException
import gr.makris.smartConnect.exceptions.confirmationToken.ConfirmationTokenNotFoundException
import lombok.AllArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@AllArgsConstructor
class ConfirmationTokenServiceImpl: ConfirmationTokenService {

    val logger: Logger = LoggerFactory.getLogger(ConfirmationTokenServiceImpl::class.java)

    @Autowired
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    @Autowired
    lateinit var userJpaRepository: DbRepository

    override fun saveConfirmationToken(token: ConfirmationToken): DataResult<ConfirmationToken, Throwable> {
        return try {
            DataResult(confirmationTokenRepository.save(token))
        } catch (t: Throwable) {
            logger.info(t.message)
            throw throw ConfirmationTokenNotFoundException(errorMessage = "There was a problem in confirmation token saving", errorCode = "CONF15")
//            DataResult(error = t)
        }
    }

    override fun confirmToken(token: String): DataResult<ConfirmationToken, Throwable> {
        try {
            val foundToken = confirmationTokenRepository.findByToken(token)
            return if (foundToken.token.isNotEmpty()) {

                if (foundToken.createdat == null) {
                    throw ConfirmationTokenNotFoundException(errorMessage = "Confirmation token not found", errorCode = "CONF10")
//                    return DataResult(error = IllegalStateException("Token not found"))
                }

                val expiredDate = foundToken.expiresat
                if (expiredDate.isBefore(LocalDateTime.now())) {
                    throw ConfirmationExpiredTokenException(errorMessage = "Confirmation token has expired", errorCode = "CONF20")
//                    return DataResult(error = IllegalStateException("Token expired"))
                }

                confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now())
                userJpaRepository.enableUser(foundToken.userid)

                DataResult(foundToken)
            } else {
                throw ConfirmationTokenNotFoundException(errorMessage = "Confirmation token not found", errorCode = "CONF10")
//                DataResult(error = IllegalStateException("Token not found"))
            }
        } catch (t: ConfirmationExpiredTokenException) {
            throw ConfirmationExpiredTokenException(errorMessage = "Confirmation token has expired", errorCode = "CONF20")
        } catch (t: Throwable) {
            throw ConfirmationTokenNotFoundException(errorMessage = "Confirmation token not found", errorCode = "CONF10")
//            return DataResult(error = IllegalStateException("Token not found"))
        }
    }
}