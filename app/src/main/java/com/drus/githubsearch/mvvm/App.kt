package com.drus.githubsearch.mvvm

import com.drus.githubsearch.core.di.BaseComponent
import com.drus.githubsearch.core.di.BaseComponentProvider
import com.drus.githubsearch.core.di.BaseModule
import com.drus.githubsearch.core.di.NavigationModule
import com.drus.githubsearch.core.di.NetworkModule
import com.drus.githubsearch.mvvm.di.AppComponent
import com.drus.githubsearch.mvvm.di.AppComponentProvider
import com.drus.githubsearch.mvvm.di.DaggerAppComponent
import com.drus.githubsearch.mvvm.di.DaggerBaseComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication(), BaseComponentProvider, AppComponentProvider {
    private lateinit var appComponent: AppComponent
    private lateinit var baseComponent: BaseComponent
    override fun onCreate() {
        initDagger()
        super.onCreate()
        appComponent.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        baseComponent = DaggerBaseComponent
            .builder()
            .baseModule(BaseModule())
            .networkModule(NetworkModule())
            .navigationModule(NavigationModule)
            .build()
    }

    override fun getBaseComponent(): BaseComponent {
        return baseComponent
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }
}