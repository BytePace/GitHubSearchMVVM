package com.drus.githubsearch.mvvm.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.search.Screens
import com.drus.githubsearch.search.screens.repositoryDetails.presentation.GithubRepositoryDetailsViewModel
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.MainScope
import javax.inject.Inject

class MainViewModel @AssistedInject constructor(
    private val router: Router,
    private val holder: NavigatorHolder,
) : BaseViewModel<MainState, MainEvent, MainCommand>() {

    private var isFirstAttach = true

    override fun initState(): MainState {
        return MainState()
    }

    override fun processEvent(event: MainEvent) {
        when(event) {

            else -> {}
        }
    }


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

    @Suppress("UNCHECKED_CAST")
    companion object {
        const val SEARCH_DEBOUNCE = 500L
        fun provideFactory(
            assistedFactory: Factory,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create() as T
            }
        }
    }
}