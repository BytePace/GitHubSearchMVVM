package com.bytepace.navigatoin

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.bytepace.githubsearch.navigation.R
import com.drus.githubsearch.core.utils.KeyboardManager
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

private const val ANIMATIONS_MIN_COUNT = 4

class Navigator(
    activity: FragmentActivity,
    @IdRes containerId: Int,
    private val animations: List<Int> = listOf()
) : AppNavigator(activity, containerId) {
    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        if (animations.size < ANIMATIONS_MIN_COUNT) return
        fragmentTransaction.setCustomAnimations(
            animations[0],
            animations[1],
            animations[2],
            animations[3]
        )
    }

    override fun applyCommands(commands: Array<out Command>) {
        KeyboardManager.hide(activity)
        super.applyCommands(commands)
    }

    companion object {
        val slideSideAwayAnimation = listOf(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )

        val slideUpAnimation = listOf(
            R.anim.slide_in_up,
            R.anim.slide_out_down,
            R.anim.slide_in_down,
            R.anim.slide_out_up
        )
    }
}