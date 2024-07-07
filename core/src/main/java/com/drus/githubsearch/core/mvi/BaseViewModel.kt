package com.drus.githubsearch.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val EFFECTS_BUFFER_CAPACITY = 100

abstract class BaseViewModel<STATE, EVENT, EFFECT> : ViewModel() {
    private val initState: STATE
        get() = initState()

    abstract fun initState(): STATE

    private val _state = MutableStateFlow(initState)
    val state get() = _state.asStateFlow()

    fun emitNewState(block: (oldState: STATE) -> STATE) =
        viewModelScope.launch {
            _state.emit(block(state.value))
        }

    suspend fun emitNewStateSync(block: (oldState: STATE) -> STATE) =
        _state.emit(block(state.value))

    val effect: Flow<EFFECT>
        get() = _effect.receiveAsFlow()
    private val _effect =
        Channel<EFFECT>(
            capacity = EFFECTS_BUFFER_CAPACITY,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    protected suspend fun sendEffect(effect: EFFECT) =
        _effect.send(effect)

    protected fun sendEffectAsync(effect: EFFECT) =
        viewModelScope.launch {
            _effect.send(effect)
        }

    abstract fun processEvent(event: EVENT)
}