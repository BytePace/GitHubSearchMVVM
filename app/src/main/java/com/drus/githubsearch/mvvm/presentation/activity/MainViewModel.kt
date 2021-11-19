package com.drus.githubsearch.mvvm.presentation.activity

import androidx.lifecycle.ViewModel
import com.drus.githubsearch.core.navigation.Navigator
import com.drus.githubsearch.mvvm.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    private val holder: NavigatorHolder
) : ViewModel() {

    private var isFirstAttach = true

    fun setNavigator(navigator: Navigator) {
        holder.removeNavigator()
        holder.setNavigator(navigator)
        if (isFirstAttach) {
            router.replaceScreen(Screens.search())
            isFirstAttach = false
        }
    }

    fun detach() {
        holder.removeNavigator()
    }
}