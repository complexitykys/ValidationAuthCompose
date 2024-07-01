package test.auth.testauth.authscreen.base

fun interface StateChange<State> {
    fun apply(state: State): State
}

class NoChanges<State> : StateChange<State> {
    override fun apply(state: State): State = state
}
