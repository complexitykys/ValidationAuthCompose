package test.auth.testauth.domain.validation

fun interface Validator<T, out E> {
    suspend fun validate(value: T): ValidationResult<T, E>
}

class FailFastValidator<T, out E>(
    private val validators: List<Validator<T, E>>
) : Validator<T, E> {
    override suspend fun validate(value: T): ValidationResult<T, E> {
        for (validator in validators) {
            when (val result = validator.validate(value)) {
                is Failure<E> -> return result
                is Success<T> -> continue
            }
        }
        return Success(value)
    }
}

class CompoundValidator<T, out E>(
    private val validators: List<Validator<T, E>>,
    private val mapper: (List<E>) -> E
) : Validator<T, E> {
    override suspend fun validate(value: T): ValidationResult<T, E> {
        val errors = validators.fold(listOf<E>()) { acc, validator ->
            when (val result = validator.validate(value)) {
                is Failure<E> -> acc + result.data
                is Success<T> -> acc
            }
        }
        return when (errors.isEmpty()) {
            true -> Success(value)
            false -> Failure(mapper(errors))
        }
    }

}