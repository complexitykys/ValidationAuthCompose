package test.auth.testauth.domain.auth

import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator

class InvalidCharsValidator(private val error: String): Validator<String, Error> {
    private val invalidChars = listOf('#', '?', '/', '\\', '}', '{', '[', ']')
    override suspend fun validate(value: String): ValidationResult<String, Error> {
        return if (invalidChars.any { value.contains(it) })
            Failure(Error(error))
        else
            Success(value)
    }
}