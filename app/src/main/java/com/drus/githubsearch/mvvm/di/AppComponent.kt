package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.mvvm.App
import com.drus.githubsearch.core.di.NavigationModule
import com.drus.githubsearch.core.di.NetworkModule
import com.drus.githubsearch.mvvm.activity.MainViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@AppScope
@Component(modules = [
    AppModule::class,
    ActivitiesModule::class,
    NetworkModule::class,
    NavigationModule::class
])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun mainViewModelFactory(): MainViewModel.Factory
}