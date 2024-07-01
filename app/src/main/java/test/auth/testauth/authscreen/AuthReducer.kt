package test.auth.testauth.authscreen

import test.auth.testauth.authscreen.base.Reducer
import test.auth.testauth.authscreen.base.StateChange
import test.auth.testauth.domain.auth.FieldError
import test.auth.testauth.domain.validation.Validator

class AuthReducer(private val context: AuthContext) : Reducer<AuthScreenState, AuthIntent> {

    override suspend fun reduce(
        oldState: AuthScreenState,
        intent: AuthIntent
    ): StateChange<AuthScreenState> {
        return intent.run { context.proceed(oldState) }
    }
}

class AuthContext(
    val fullNameValidator: Validator<String, FieldError>,
    val usernameValidator: Validator<String, FieldError>,
    val dateOfBirthValidator: Validator<String, FieldError>,
    val passwordValidator: Validator<String, FieldError>,
    val confirmPasswordValidator: Validator<String, FieldError>
)
