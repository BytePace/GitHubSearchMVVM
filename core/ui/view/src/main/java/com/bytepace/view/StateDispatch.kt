package com.bytepace.view

import kotlinx.coroutines.flow.FlowCollector

data class StateDispatch<STATE, EVENT>(
    val stateSubscription: suspend (FlowCollector<STATE>) -> Nothing,
    val dispatch: (EVENT) -> Unit,
)
