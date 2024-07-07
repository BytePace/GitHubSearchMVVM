package com.drus.githubsearch.core.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardManager {

    fun hide(activity: Activity?) {
        hide(activity?.window?.decorView?.rootView, null)
    }

    fun hide(activity: Activity?, onFinish: (() -> Unit)?) {
        hide(activity?.window?.decorView?.rootView, onFinish)
    }

    fun hide(dialog: Dialog?, onFinish: (() -> Unit)?) {
        hide(dialog?.currentFocus, onFinish)
    }

    fun hide(view: View?, onFinish: (() -> Unit)?) {
        if (view != null) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(
                view.windowToken,
                0,
                object : ResultReceiver(
                    Handler(Looper.getMainLooper())
                ) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        onFinish?.invoke()
                    }
                }
            )
        } else {
            onFinish?.invoke()
        }
    }

    fun show(view: View?) {
        view?.let {
            it.requestFocus()
            (it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }
    }
}