package test.auth.testauth.authscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.auth.testauth.authscreen.utils.StringResourceManager
import test.auth.testauth.domain.auth.EmptyFieldValidator
import test.auth.testauth.domain.auth.FullNameValidator
import test.auth.testauth.domain.auth.InvalidCharsValidator
import test.auth.testauth.domain.auth.MismatchValidator
import test.auth.testauth.domain.auth.UnderageValidator
import test.auth.testauth.domain.auth.WeekPasswordValidator
import test.auth.testauth.domain.validation.FailFastValidator

class AuthScreenViewModel(stringResourceManager: StringResourceManager) : ViewModel() {

    private val reducer: Reducer<AuthIntent, AuthScreenState> = AuthReducer(
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
                MismatchValidator(stringResourceManager.mismatchPassword) { _authScreenState.value.password }
            )
        )
    )

    private val _authScreenState = MutableStateFlow(AuthScreenState.INIT_STATE)

    val authScreenState: StateFlow<AuthScreenState> = _authScreenState.asStateFlow()

    fun reduceIntent(action: AuthIntent) {
        viewModelScope.launch {
            _authScreenState.update { reducer.reduce(action, it) }
        }
    }
}