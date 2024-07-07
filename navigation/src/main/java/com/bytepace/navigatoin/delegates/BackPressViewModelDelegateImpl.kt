package com.bytepace.navigatoin.delegates

import com.bytepace.horoscope.navigation.delegates.BackPressViewModelDelegate
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class BackPressViewModelDelegateImpl @Inject constructor(
    private val router: Router
) : BackPressViewModelDelegate {

    override fun onBackClicked() {
        router.exit()
    }
}