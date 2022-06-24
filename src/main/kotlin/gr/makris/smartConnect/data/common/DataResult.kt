package gr.atcom.gpslocationservice.model.common

data class DataResult<out T, out E : Throwable>(
    val data: T? = null,
    val error: E? = null
)

data class DataResultWithError<out T, out E : Model>(
    val data: T? = null,
    val error: E? = null
)
