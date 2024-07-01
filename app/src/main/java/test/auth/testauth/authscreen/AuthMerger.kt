package test.auth.testauth.authscreen

import test.auth.testauth.authscreen.base.StateChange
import test.auth.testauth.authscreen.base.StateMerger
import test.auth.testauth.domain.validation.Success
import test.auth.testauth.domain.validation.ValidationResult

class AuthMerger : StateMerger<AuthScreenState> {

    override suspend fun merge(
        state: AuthScreenState,
        changes: StateChange<AuthScreenState>
    ): AuthScreenState {
        val newState = changes.apply(state)
        return newState.copy(
            isButtonEnabled = calculateButtonEnabled(newState)
        )
    }

    private fun calculateButtonEnabled(newState: AuthScreenState): Boolean {
        return newState.run {
            fullNameValidationState.isNotBlankAndSuccess() &&
                    usernameValidationState.isNotBlankAndSuccess() &&
                    dateOfBirthValidationState.isNotBlankAndSuccess() &&
                    passwordValidationState.isNotBlankAndSuccess() &&
                    mismatchValidationState.isNotBlankAndSuccess()
        }
    }

    private fun ValidationResult<String, *>.isNotBlankAndSuccess(): Boolean {
        return this is Success && this.data.isNotBlank()
    }
}