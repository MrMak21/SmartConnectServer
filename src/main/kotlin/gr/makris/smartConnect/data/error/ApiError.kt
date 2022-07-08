package gr.makris.smartConnect.data.error

import gr.atcom.gpslocationservice.model.common.Model
import org.springframework.http.HttpStatus
import java.time.Instant

data class ApiError(
    val status: HttpStatus,
    val timestamp: String,
    val errors: List<CustomError>
): Model
