package com.bytepace.view

import androidx.lifecycle.LifecycleOwner
import com.drus.githubsearch.core.mvi.EventsDispatcherController
import com.drus.githubsearch.core.mvi.UnidirectionalController
import kotlinx.coroutines.flow.FlowCollector


inline fun <reified STATE, EVENT, EFFECT> LifecycleOwner.use(
    viewModel: EventsDispatcherController<STATE, EVENT, EFFECT>,
): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val stateSubscription = viewModel.state::collect

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatchEffect(
        stateSubscription = stateSubscription,
        effectFlow = viewModel.effect,
        dispatch = dispatch,
    )
}


inline fun <reified STATE, EVENT> use(
    viewModel: UnidirectionalController<STATE, EVENT>,
): StateDispatch<STATE, EVENT> {
    val stateSubscription = viewModel.state::collect

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }

    return StateDispatch(
        stateSubscription = stateSubscription,
        dispatch = dispatch,
    )
}
