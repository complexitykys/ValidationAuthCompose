package test.auth.testauth.authscreen.base

fun interface Reducer<State, Intent> {
    suspend fun reduce(oldState: State, intent: Intent) : StateChange<State>
}