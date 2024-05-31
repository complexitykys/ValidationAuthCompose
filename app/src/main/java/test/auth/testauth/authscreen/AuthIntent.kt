package test.auth.testauth.authscreen

sealed interface AuthIntent : Intent

@JvmInline
value class ChangeFullName(val newFullName: String) : AuthIntent
@JvmInline
value class ChangeUsername(val newUsername: String) : AuthIntent
@JvmInline
value class ChangeDateOfBirth(val newDateOfBirth: String) : AuthIntent
@JvmInline
value class ChangePassword(val newPassword: String) : AuthIntent
@JvmInline
value class ChangeConfirmPassword(val newPassword: String) : AuthIntent
@JvmInline
value class ChangeButtonState(val inEnabled: Boolean) : AuthIntent
data object TogglePasswordVisibility : AuthIntent
data object ToggleConfirmPasswordVisibility : AuthIntent
