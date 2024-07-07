package com.bytepace.compose

data class StateDispatch<STATE, EVENT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
)
