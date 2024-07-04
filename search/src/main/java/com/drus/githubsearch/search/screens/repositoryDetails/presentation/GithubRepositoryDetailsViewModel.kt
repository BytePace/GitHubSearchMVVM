package com.drus.githubsearch.search.screens.repositoryDetails.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.drus.githubsearch.core.presentation.BaseViewModel
import com.drus.githubsearch.search.screens.search.data.models.SimpleRepositoryInfo
import com.drus.githubsearch.search.screens.search.domain.GitHubRepository
import com.drus.githubsearch.search.screens.search.presentation.SearchGithubRepositoryViewModel
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepositoryDetailsViewModel @AssistedInject constructor(
    private val githubRepository: GitHubRepository,
    private val router: Router
) : BaseViewModel<GithubRepositoryDetailsState, GithubRepositoryDetailsEvent, GithubRepositoryDetailsCommand>() {

    override fun initState(): GithubRepositoryDetailsState {
        TODO("Not yet implemented")
    }

    init {

    }

    override fun processEvent(event: GithubRepositoryDetailsEvent) {
        TODO("Not yet implemented")
    }

    val info = MutableLiveData<SimpleRepositoryInfo>()
    val date = MutableLiveData<String>()

    fun startInit(info: SimpleRepositoryInfo) {
        this.info.value = info
        viewModelScope.launch(Dispatchers.IO) {
            val details = githubRepository.getDetails(info)
            withContext(Dispatchers.Main) {
                date.value = details?.commit?.details?.author?.date
            }
        }
    }

    fun back() {
        router.newChain()
    }

    @AssistedFactory
    interface Factory {
        fun create(): GithubRepositoryDetailsViewModel
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