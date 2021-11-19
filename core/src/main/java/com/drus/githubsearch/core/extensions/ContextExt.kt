package com.drus.githubsearch.core.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

@Suppress("UNCHECKED_CAST", "EXTENSION_SHADOWED_BY_MEMBER")
fun <T> Context.getSystemService(id: String): T {
    return getSystemService(id) as T
}

fun Context.showKeyboard(field: EditText) {
    field.requestFocus()
    getSystemService<InputMethodManager>(Context.INPUT_METHOD_SERVICE)
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context.hideKeyboard(view: View) {
    view.clearFocus()
    getSystemService<InputMethodManager>(Context.INPUT_METHOD_SERVICE)
        .hideSoftInputFromWindow(view.windowToken, 0)
}