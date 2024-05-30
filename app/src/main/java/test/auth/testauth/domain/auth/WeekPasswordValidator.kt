package test.auth.testauth.domain.auth

import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator

class WeekPasswordValidator(private val error: String) : Validator<String, Error> {
    private val regex = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}$")

    override suspend fun validate(value: String): ValidationResult<String, Error> {
        if (value.length <= 8) {
            return Failure(Error(error))
        }
        return if (regex.containsMatchIn(value)) Success(value) else Failure(Error(error))
    }
}