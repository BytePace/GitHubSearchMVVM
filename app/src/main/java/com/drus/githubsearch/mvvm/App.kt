package com.drus.githubsearch.mvvm

import android.app.Application
import com.drus.githubsearch.core.di.BaseComponent
import com.drus.githubsearch.core.di.BaseComponentProvider
import com.drus.githubsearch.core.di.BaseModule
import com.drus.githubsearch.core.di.DaggerBaseComponent
import com.drus.githubsearch.core.di.NavigationModule
import com.drus.githubsearch.core.di.NetworkModule
import com.drus.githubsearch.mvvm.di.AppComponent
import com.drus.githubsearch.mvvm.di.AppComponentProvider
import com.drus.githubsearch.mvvm.di.DaggerAppComponent
import com.drus.githubsearch.search.di.DaggerSearchGithubRepositoryComponent
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponent
import com.drus.githubsearch.search.di.SearchGithubRepositoryComponentProvider
import com.drus.githubsearch.search.di.SearchGithubRepositoryModule

class App : Application(), AppComponentProvider,
    BaseComponentProvider, SearchGithubRepositoryComponentProvider {
    private lateinit var appComponent: AppComponent
    private lateinit var baseComponent: BaseComponent
    private lateinit var searchGithubRepositoryComponent: SearchGithubRepositoryComponent
    override fun onCreate() {
        initDagger()
        super.onCreate()
    }

    private fun initDagger() {
        baseComponent = DaggerBaseComponent.builder()
            .baseModule(BaseModule(this))
            .networkModule(NetworkModule())
            .navigationModule(NavigationModule())
            .build()
        appComponent = DaggerAppComponent.builder()
            .baseDependencies(baseComponent)
            .build()
            //TODO вынести создание featureComponent в feature module
        searchGithubRepositoryComponent = DaggerSearchGithubRepositoryComponent.builder()
            .searchGithubRepositoryModule(SearchGithubRepositoryModule())
            .baseDependencies(baseComponent)
            .build()
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }

    override fun getBaseComponent(): BaseComponent {
        return baseComponent
    }

    override fun getSearchGithubRepositoryComponent(): SearchGithubRepositoryComponent {
       return searchGithubRepositoryComponent
    }
}