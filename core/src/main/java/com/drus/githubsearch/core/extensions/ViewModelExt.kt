package com.drus.githubsearch.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

fun ViewModel.launch(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(context, block = block)
}

fun ViewModel.launch(
    exceptionHandler: CoroutineExceptionHandler,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(exceptionHandler, block = block)
}