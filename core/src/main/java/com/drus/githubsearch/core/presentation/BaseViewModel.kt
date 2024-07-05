package com.drus.githubsearch.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private const val COMMAND_BUFFER_CAPACITY = 100

abstract class BaseViewModel<ST, EV, CM> : ViewModel() {
    private val initState: ST
        get() = initState()
    private val _state = MutableStateFlow(initState)
    val state get() = _state.asStateFlow()
    abstract fun initState(): ST

    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun emitNewState(block: (oldState: ST) -> ST) =
        viewModelScope.launch {
            _state.emit(block(state.value))
        }

    suspend fun emitNewStateSync(block: (oldState: ST) -> ST) =
        _state.emit(block(state.value))

    val command: Flow<CM>
        get() = _command.receiveAsFlow()
    private val _command =
        Channel<CM>(
            capacity = COMMAND_BUFFER_CAPACITY,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    protected suspend fun sendCommand(command: CM) =
        _command.send(command)

    protected fun sendCommandAsync(command: CM) =
        viewModelScope.launch {
            _command.send(command)
        }

    abstract fun processEvent(event: EV)

    fun launchViewModelScope(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context = context, start = start, block = block)
}