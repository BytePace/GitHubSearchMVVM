package com.bytepace.view

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow

data class StateDispatchEffect<STATE, EVENT, EFFECT>(
    val stateSubscription: suspend (FlowCollector<STATE>) -> Nothing,
    val dispatch: (EVENT) -> Unit,
    val effectFlow: SharedFlow<EFFECT>
)
