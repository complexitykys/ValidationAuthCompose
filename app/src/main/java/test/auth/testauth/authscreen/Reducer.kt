package test.auth.testauth.authscreen

interface Reducer<T: Intent, State> {
    suspend fun reduce(action: T, old: State) : State
}