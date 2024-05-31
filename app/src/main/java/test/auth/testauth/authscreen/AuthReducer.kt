package test.auth.testauth.authscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.auth.testauth.domain.auth.FieldError
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult
import test.auth.testauth.domain.validation.Validator

class AuthReducer(
    private val fullNameValidator: Validator<String, FieldError>,
    private val usernameValidator: Validator<String, FieldError>,
    private val dateOfBirthValidator: Validator<String, FieldError>,
    private val passwordValidator: Validator<String, FieldError>,
    private val confirmPasswordValidator: Validator<String, FieldError>,
) : Reducer<AuthIntent, AuthScreenState> {
    override suspend fun reduce(action: AuthIntent, old: AuthScreenState): AuthScreenState {
        return when(action) {
            is ChangeFullName -> {
                val fullNameValidationState = withContext(Dispatchers.Main.immediate) {
                    fullNameValidator.validate(action.newFullName)
                }
                old.copy(
                    fullName = action.newFullName,
                    fullNameValidationState = fullNameValidationState
                ).validateButtonAccess()
            }
            is ChangeUsername -> {
                val usernameValidationState = withContext(Dispatchers.Main.immediate) {
                    usernameValidator.validate(action.newUsername)
                }
                old.copy(
                    username = action.newUsername,
                    usernameValidationState = usernameValidationState
                ).validateButtonAccess()
            }
            is ChangeDateOfBirth -> {
                val dateOfBirtValidationState = withContext(Dispatchers.Main.immediate) {
                    dateOfBirthValidator.validate(action.newDateOfBirth)
                }
                old.copy(
                    dateOfBirth = action.newDateOfBirth,
                    dateOfBirthValidationState = dateOfBirtValidationState
                ).validateButtonAccess()
            }
            is ChangePassword -> {
                val passwordValidationState = withContext(Dispatchers.Main.immediate) {
                    passwordValidator.validate(action.newPassword)
                }
                old.copy(
                    password = action.newPassword,
                    passwordValidationState = passwordValidationState
                ).validateButtonAccess()
            }
            is ChangeConfirmPassword -> {
                val mismatchValidationState = withContext(Dispatchers.Main.immediate) {
                    confirmPasswordValidator.validate(action.newPassword)
                }
                old.copy(
                    confirmPassword = action.newPassword,
                    mismatchValidationState = mismatchValidationState
                ).validateButtonAccess()
            }
            is ChangeButtonState -> {
                old.copy(
                    isButtonEnabled = action.inEnabled
                ).validateButtonAccess()
            }
            TogglePasswordVisibility -> {
                old.copy(showPassword = !old.showPassword)
            }
            ToggleConfirmPasswordVisibility -> {
                old.copy(showConfirmPassword = !old.showConfirmPassword)
            }
        }
    }
}

private fun ValidationResult<String, *>.isNotBlankAndSuccess(): Boolean {
    return this is Success && this.data.isNotBlank()
}


fun AuthScreenState.validateButtonAccess() = copy(
    isButtonEnabled = fullNameValidationState.isNotBlankAndSuccess() &&
            usernameValidationState.isNotBlankAndSuccess() &&
            dateOfBirthValidationState.isNotBlankAndSuccess() &&
            passwordValidationState.isNotBlankAndSuccess() &&
            mismatchValidationState.isNotBlankAndSuccess()
)