package test.auth.testauth.authscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.auth.testauth.authscreen.base.Intent
import test.auth.testauth.authscreen.base.StateChange

sealed interface AuthIntent : Intent<AuthScreenState, AuthContext>

@JvmInline
value class ChangeFullName(private val newFullName: String) : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        val fullNameValidationState = withContext(Dispatchers.Main.immediate) {
            fullNameValidator.validate(newFullName)
        }
        return StateChange { currentState ->
            currentState.copy(
                fullName = newFullName,
                fullNameValidationState = fullNameValidationState
            )
        }
    }
}


@JvmInline
value class ChangeUsername(private val newUsername: String) : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        val usernameValidationState = withContext(Dispatchers.Main.immediate) {
            usernameValidator.validate(newUsername)
        }
        return StateChange { currentState ->
            currentState.copy(
                username = newUsername,
                usernameValidationState = usernameValidationState
            )
        }
    }
}

@JvmInline
value class ChangeDateOfBirth(private val newDateOfBirth: String) : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        val dateOfBirthValidationState = withContext(Dispatchers.Main.immediate) {
            dateOfBirthValidator.validate(newDateOfBirth)
        }
        return StateChange { currentState ->
            currentState.copy(
                dateOfBirth = newDateOfBirth,
                dateOfBirthValidationState = dateOfBirthValidationState
            )
        }
    }
}

@JvmInline
value class ChangePassword(private val newPassword: String) : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        val passwordValidationState = withContext(Dispatchers.Main.immediate) {
            passwordValidator.validate(newPassword)
        }
        return StateChange { currentState ->
            currentState.copy(
                password = newPassword,
                passwordValidationState = passwordValidationState
            )
        }
    }
}

@JvmInline
value class ChangeConfirmPassword(private val newPassword: String) : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        val mismatchValidationState = withContext(Dispatchers.Main.immediate) {
            confirmPasswordValidator.validate(newPassword)
        }
        return StateChange { currentState ->
            currentState.copy(
                confirmPassword = newPassword,
                mismatchValidationState = mismatchValidationState
            )
        }
    }
}

data object TogglePasswordVisibility : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        return StateChange { currentState ->
            currentState.copy(showPassword = !currentState.showPassword)
        }
    }
}

data object ToggleConfirmPasswordVisibility : AuthIntent {
    override suspend fun AuthContext.proceed(state: AuthScreenState): StateChange<AuthScreenState> {
        return StateChange { currentState ->
            currentState.copy(showConfirmPassword = !currentState.showConfirmPassword)
        }
    }
}
