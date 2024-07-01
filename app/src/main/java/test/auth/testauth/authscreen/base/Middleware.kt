package test.auth.testauth.authscreen.base

fun interface Middleware<State> {
    suspend fun intercept(state: State)
}