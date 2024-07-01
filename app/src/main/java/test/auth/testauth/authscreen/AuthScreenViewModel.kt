package test.auth.testauth.authscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import test.auth.testauth.authscreen.base.MVIComponent
import test.auth.testauth.authscreen.utils.StringResourceManager
import test.auth.testauth.domain.auth.EmptyFieldValidator
import test.auth.testauth.domain.auth.FullNameValidator
import test.auth.testauth.domain.auth.InvalidCharsValidator
import test.auth.testauth.domain.auth.MismatchValidator
import test.auth.testauth.domain.auth.UnderageValidator
import test.auth.testauth.domain.auth.WeekPasswordValidator
import test.auth.testauth.domain.validation.FailFastValidator
import test.auth.testauth.domain.validation.Success

class AuthScreenViewModel(
    stringResourceManager: StringResourceManager
) : ViewModel() {

    private val context = AuthContext(
        FailFastValidator(
            listOf(
                EmptyFieldValidator(stringResourceManager.emptyField),
                FullNameValidator(stringResourceManager.incorrectFullName),
            )
        ),
        FailFastValidator(
            listOf(
                EmptyFieldValidator(stringResourceManager.emptyField),
                InvalidCharsValidator(stringResourceManager.invalidCharacters)
            )
        ),
        FailFastValidator(
            listOf(
                EmptyFieldValidator(stringResourceManager.emptyField),
                UnderageValidator(stringResourceManager.underage)
            )
        ),
        FailFastValidator(
            listOf(
                EmptyFieldValidator(stringResourceManager.emptyField),
                WeekPasswordValidator(stringResourceManager.weekPassword)
            )
        ),
        FailFastValidator(
            listOf(
                EmptyFieldValidator(stringResourceManager.emptyField),
                MismatchValidator(stringResourceManager.mismatchPassword) { state.value.password }
            )
        )
    )

    private val mviComponent = MVIComponent(
        initialState = AuthScreenState(
            fullName = "",
            username = "",
            dateOfBirth = "",
            password = "",
            confirmPassword = "",
            isButtonEnabled = false,
            showPassword = false,
            showConfirmPassword = false,
            fullNameValidationState = Success(""),
            usernameValidationState = Success(""),
            dateOfBirthValidationState = Success(""),
            passwordValidationState = Success(""),
            mismatchValidationState = Success("")
        ),
        coroutineScope = viewModelScope,
        reducer = AuthReducer(context),
        stateMerger = AuthMerger()
    )

    val state: StateFlow<AuthScreenState> = mviComponent.state
    fun reduce(intent: AuthIntent) {
        viewModelScope.launch {
            mviComponent.reduce(intent)
        }
    }
}