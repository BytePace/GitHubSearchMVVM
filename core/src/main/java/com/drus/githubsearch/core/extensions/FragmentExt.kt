package com.drus.githubsearch.core.extensions

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(requireContext(), text, duration).apply {
        show()
    }
}

fun Fragment.showToast(@StringRes text: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(requireContext(), text, duration).apply {
        show()
    }
}

val Fragment.fragmentScope: CoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

fun Fragment.forceRefresh() {
    parentFragmentManager
        .beginTransaction()
        .detach(this)
        .attach(this)
        .commit()
}

fun <T: Activity> Fragment.addOnBackPressedCallback(callback: (T?) -> Unit) {
    activity?.onBackPressedDispatcher?.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback(requireActivity() as? T)
            }
        })
}