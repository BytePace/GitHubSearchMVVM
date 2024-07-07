package com.drus.githubsearch.core.mvi

import kotlinx.coroutines.flow.SharedFlow

interface EventsDispatcherController<STATE, EVENT, EFFECT> : UnidirectionalController<STATE, EVENT> {
    val effect: SharedFlow<EFFECT>
}