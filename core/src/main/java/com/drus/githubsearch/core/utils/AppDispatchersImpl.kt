package com.drus.githubsearch.core.utils

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchersImpl(
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
    override val unconfined: CoroutineDispatcher,
    override val main: CoroutineDispatcher
) : AppDispatchers