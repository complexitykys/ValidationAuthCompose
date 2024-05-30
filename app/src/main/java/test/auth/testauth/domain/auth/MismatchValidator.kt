package test.auth.testauth.domain.auth

import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator

class MismatchValidator(
    private val error: String,
    val comparedValue: () -> String,
) : Validator<String, Error> {
    override suspend fun validate(value: String): ValidationResult<String, Error> {
        return if (value == comparedValue())
            Success(value)
        else
            Failure(Error(error))
    }
}