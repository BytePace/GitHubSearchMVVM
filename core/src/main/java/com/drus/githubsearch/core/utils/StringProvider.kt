package com.drus.githubsearch.core.utils

import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg args: Any): String

    fun getStringArray(@ArrayRes id: Int): Array<String>

    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any): String
}