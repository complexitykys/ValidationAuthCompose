package test.auth.testauth.authscreen.base

fun interface Intent<State, Context> {
    suspend fun Context.proceed(state: State) : StateChange<State>
}