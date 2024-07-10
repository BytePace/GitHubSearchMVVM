package com.drus.githubsearch.mvvm

import android.os.Bundle
import android.view.WindowManager
import com.bytepace.navigatoin.Navigator
import com.drus.githubsearch.search.FlowScreen
import com.drus.githubsearch.search.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AppActivity : DaggerAppCompatActivity(R.layout.activity_main) {

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    @Inject
    internal lateinit var router: Router

    private val navigator = Navigator(this, R.id.fragment_container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) return

        router.newRootScreen(FlowScreen)
    }

    @Suppress("DEPRECATION")
    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onResumeFragments() {
        navigatorHolder.removeNavigator()
        super.onResumeFragments()
    }
}