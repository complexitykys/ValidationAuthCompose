package test.auth.testauth.authscreen

sealed interface AuthIntent : Intent

data class ChangeFullName(val newFullName: String) : AuthIntent
data class ChangeUsername(val newUsername: String) : AuthIntent
data class ChangeDateOfBirth(val newDateOfBirth: String) : AuthIntent
data class ChangePassword(val newPassword: String) : AuthIntent
data class ChangeConfirmPassword(val newPassword: String) : AuthIntent
data class ChangeButtonState(val inEnabled: Boolean) : AuthIntent
data object TogglePasswordVisibility: AuthIntent
data object ToggleConfirmPasswordVisibility: AuthIntent