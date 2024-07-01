package test.auth.testauth.authscreen.base

fun interface StateMerger<State> {
    suspend fun merge(state: State, changes: StateChange<State>) : State
}