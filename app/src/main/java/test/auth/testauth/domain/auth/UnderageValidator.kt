package test.auth.testauth.domain.auth

import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator
import java.time.LocalDate
import java.time.Period

class UnderageValidator(private val error: String): Validator<String, Error> {
    override suspend fun validate(value: String): ValidationResult<String, Error> {
        val dateOfBirth = LocalDate.parse(value)
        val now = LocalDate.now()
        val age = Period.between(dateOfBirth, now).years
        return if (age >= 18) Success(value) else Failure(Error(error))
    }
}