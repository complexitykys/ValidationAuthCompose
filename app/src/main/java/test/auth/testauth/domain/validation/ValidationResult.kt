package test.auth.testauth.domain.validation

sealed interface ValidationResult<out T, out E>

data class Success<T>(val data: T) : ValidationResult<T, Nothing>

data class Failure<E>(val data: E) : ValidationResult<Nothing, E>

inline fun <T, T1, E> ValidationResult<T, E>.map(block: (T) -> T1): ValidationResult<T1, E> {
    return when (this) {
        is Success<T> -> Success(block(data))
        is Failure<E> -> this
    }
}

inline fun <T, E, E1> ValidationResult<T, E>.mapError(block: (E) -> E1): ValidationResult<T, E1> {
    return when (this) {
        is Success<T> -> this
        is Failure<E> -> Failure(block(data))
    }
}

val ValidationResult<*, *>.isFailure
    get() = this is Failure<*>