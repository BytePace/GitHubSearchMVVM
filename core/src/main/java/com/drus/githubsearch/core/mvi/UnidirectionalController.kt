package com.drus.githubsearch.core.mvi

import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalController<STATE, EVENT> {
    val state: StateFlow<STATE>
    fun event(event: EVENT)
}