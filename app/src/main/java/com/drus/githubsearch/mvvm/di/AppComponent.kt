package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.mvvm.activity.MainViewModel
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
    ], dependencies = [BaseDependencies::class]
)
interface AppComponent : BaseDependencies {
    fun mainViewModelFactory(): MainViewModel.Factory
    override val router: Router
    override val retrofit: Retrofit
}