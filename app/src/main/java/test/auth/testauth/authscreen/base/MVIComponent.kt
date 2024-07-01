package test.auth.testauth.authscreen.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIComponent<State: Any, I: Intent<State, *>>(
    initialState: State,
    coroutineScope: CoroutineScope,
    private val reducer: Reducer<State, I>,
    private val stateMerger: StateMerger<State>,
    private val middlewares: List<Middleware<State>> = emptyList()
) {

    private val _state = MutableStateFlow(initialState)

    @OptIn(ObsoleteCoroutinesApi::class)
    private val reducerActor: SendChannel<I> = coroutineScope.actor(Dispatchers.IO) {
        for (intent in channel) {
            launch {
                val change = reducer.reduce(_state.first(), intent)
                _state.update {
                    val state = stateMerger.merge(it, change)
                    middlewareActor.send(state)
                    state
                }
            }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private val middlewareActor = coroutineScope.actor<State>(Dispatchers.IO) {
        for (intent in channel) {
            middlewares.forEach { it.intercept(intent) }
        }
    }

    suspend fun reduce(intent: I) {
        reducerActor.send(intent)
    }

    val state = _state.asStateFlow()
}