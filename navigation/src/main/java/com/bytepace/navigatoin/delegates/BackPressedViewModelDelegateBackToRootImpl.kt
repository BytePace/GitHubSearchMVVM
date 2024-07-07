package com.bytepace.horoscope.navigation.delegates

import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class BackPressedViewModelDelegateBackToRootImpl @Inject constructor(
    private val router: Router
): BackPressViewModelDelegate {

    override fun onBackClicked() {
        router.backTo(null)
    }
}