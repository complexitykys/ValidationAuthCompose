package test.auth.testauth.authscreen

import test.auth.testauth.domain.auth.FieldError
import test.auth.testauth.domain.validation.ValidationResult

data class AuthScreenState(
    val fullName: String,
    val username: String,
    val dateOfBirth: String,
    val password: String,
    val confirmPassword: String,
    val isButtonEnabled: Boolean,
    val showPassword: Boolean,
    val showConfirmPassword: Boolean,
    val fullNameValidationState: ValidationResult<String, FieldError>,
    val usernameValidationState: ValidationResult<String, FieldError>,
    val dateOfBirthValidationState: ValidationResult<String, FieldError>,
    val passwordValidationState: ValidationResult<String, FieldError>,
    val mismatchValidationState: ValidationResult<String, FieldError>
)