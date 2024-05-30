package test.auth.testauth.domain.auth

import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator

class EmptyFieldValidator(private val error: String) : Validator<String, Error> {
    override suspend fun validate(value: String): ValidationResult<String, Error> {
        return when(value.isBlank()) {
            true -> Failure(Error(error))
            else -> Success(value)
        }
    }
}