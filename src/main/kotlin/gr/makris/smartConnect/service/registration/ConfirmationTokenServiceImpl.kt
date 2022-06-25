package gr.makris.smartConnect.service.registration

import gr.atcom.gpslocationservice.model.common.DataResult
import gr.makris.smartConnect.repository.DbRepository
import gr.makris.smartConnect.repository.registration.ConfirmationTokenRepository
import gr.makris.smartConnect.tokens.registration.ConfirmationToken
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.time.LocalDateTime

@Service
@AllArgsConstructor
class ConfirmationTokenServiceImpl: ConfirmationTokenService {

    @Autowired
    lateinit var confirmationTokenRepository: ConfirmationTokenRepository

    @Autowired
    lateinit var userJpaRepository: DbRepository

    override fun saveConfirmationToken(token: ConfirmationToken): DataResult<ConfirmationToken, Throwable> {
        return try {
            DataResult(confirmationTokenRepository.save(token))
        } catch (t: Throwable) {
            println( t.message)
            DataResult(error = t)
        }
    }

    override fun confirmToken(token: String): DataResult<ConfirmationToken, Throwable> {
        try {
            val foundToken = confirmationTokenRepository.findByToken(token)
            return if (foundToken.token.isNotEmpty()) {

                if (foundToken.createdat == null) {
                    return DataResult(error = IllegalStateException("Token not found"))
                }

                val expiredDate = foundToken.expiresat
                if (expiredDate.isBefore(LocalDateTime.now())) {
                    return DataResult(error = IllegalStateException("Token expired"))
                }

                confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now())
                userJpaRepository.enableUser(foundToken.userid)


                DataResult(foundToken)
            } else {
                DataResult(error = IllegalStateException("Token not found"))
            }
        } catch (t: Throwable) {
            return DataResult(error = IllegalStateException("Token not found"))
        }
    }



//    try {
//        val foundToken = confirmationTokenRepository.findByToken(token)
//        if (foundToken.token.isNotEmpty()) {
//
////                if (foundToken.createdat == null) {
////                    return DataResult(error = IllegalStateException("token not found"))
////                }
////
////                val expiredDate = foundToken.expiresat
////                if (expiredDate.isBefore(LocalDateTime.now())) {
////                    return DataResult(error = IllegalStateException("token expired"))
////                }
//
//            return DataResult(foundToken)
//
//
//        } else {
//            return DataResult(error = Throwable("Token not found"))
//        }
//    } catch (t: Throwable) {
//        return DataResult(error = t)
//    }
}