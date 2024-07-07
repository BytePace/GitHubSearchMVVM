package com.bytepace.horoscope.navigation.delegates

import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class BackPressUiDelegate(
    fragment: Fragment,
    private val viewModel: BackPressViewModelDelegate,
    backButton: View? = null
) {
    init {
        backButton?.setOnClickListener {
            viewModel.onBackClicked()
        }
        fragment.requireActivity().onBackPressedDispatcher.addCallback(
            fragment.viewLifecycleOwner
        ) {
            if (isEnabled) viewModel.onBackClicked()
        }
    }
}