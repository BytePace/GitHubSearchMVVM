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
import com.drus.githubsearch.search.di.SearchComponentDependencies
import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit

class App : Application(), AppComponentProvider,
    BaseComponentProvider {
    private lateinit var appComponent: AppComponent
    private lateinit var baseComponent: BaseComponent
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
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }

    override fun getBaseComponent(): BaseComponent {
        return baseComponent
    }
}