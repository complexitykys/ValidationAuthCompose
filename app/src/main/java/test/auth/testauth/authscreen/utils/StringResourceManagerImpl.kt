package test.auth.testauth.authscreen.utils

import android.content.Context
import test.auth.testauth.R

class StringResourceManagerImpl(private val context: Context) : StringResourceManager {
    override val emptyField: String
        get() = context.getString(R.string.empty_Error)
    override val incorrectFullName: String
        get() = context.getString(R.string.incorrect_fullnameError)
    override val invalidCharacters: String
        get() = context.getString(R.string.invalidCharacters_usernameError)
    override val mismatchPassword: String
        get() = context.getString(R.string.mismatch_passwordError)
    override val underage: String
        get() = context.getString(R.string.underage_error)
    override val weekPassword: String
        get() = context.getString(R.string.weak_passwordError)
}