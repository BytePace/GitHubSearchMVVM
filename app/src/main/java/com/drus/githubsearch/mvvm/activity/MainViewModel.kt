package com.drus.githubsearch.mvvm.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drus.githubsearch.core.mvi.BaseViewModel
import com.drus.githubsearch.core.mvi.UnidirectionalController
import com.drus.githubsearch.search.Screens
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow

class MainViewModel @AssistedInject constructor(
    private val router: Router,
    private val holder: NavigatorHolder,
) : ViewModel() {
    private var isFirstAttach = true

    fun setNavigator(navigator: Navigator) {
        holder.removeNavigator()
        holder.setNavigator(navigator)
        if (isFirstAttach) {
            router.newRootChain(Screens.searchFlow())
            isFirstAttach = false
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MainViewModel
    }


    companion object {
        const val SEARCH_DEBOUNCE = 500L

        @Suppress("UNCHECKED_CAST")
        fun fromFactory(
            assistedFactory: Factory,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }
}