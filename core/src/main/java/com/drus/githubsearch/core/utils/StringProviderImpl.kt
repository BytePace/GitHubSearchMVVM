package com.drus.githubsearch.core.utils

import android.content.Context

class StringProviderImpl(
    private val context: Context
) : StringProvider {

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg args: Any): String = context.getString(id, *args)

    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)

    override fun getQuantityString(
        id: Int,
        quantity: Int
    ) = context.resources.getQuantityString(id, quantity)

    override fun getQuantityString(
        id: Int,
        quantity: Int,
        vararg args: Any
    ) = context.resources.getQuantityString(id, quantity, *args)
}