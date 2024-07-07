package com.bytepace.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drus.githubsearch.core.mvi.EventsDispatcherController
import com.drus.githubsearch.core.mvi.UnidirectionalController
import kotlinx.coroutines.flow.SharedFlow

@Composable
inline fun <reified STATE, EVENT, EFFECT> use(
    viewModel: EventsDispatcherController<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatchEffect(
        state = state,
        effectFlow = viewModel.effect,
        dispatch = dispatch,
    )
}

@Composable
inline fun <reified STATE, EVENT> use(
    viewModel: UnidirectionalController<STATE, EVENT>,
): StateDispatch<STATE, EVENT> {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatch(
        state = state,
        dispatch = dispatch,
    )
}

@Suppress("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    val sharedFlow = this
    LaunchedEffect(key1 = sharedFlow) {
        sharedFlow.collect(function)
    }
}