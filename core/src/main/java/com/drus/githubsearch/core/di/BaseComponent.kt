package com.drus.githubsearch.core.di

import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [BaseModule::class, NetworkModule::class, NavigationModule::class])
interface BaseComponent: BaseDependencies {
    override val router: Router
    override val retrofit: Retrofit
    override val navigationHolder: NavigatorHolder
}

interface BaseDependencies {
    val router: Router
    val navigationHolder: NavigatorHolder
    val retrofit: Retrofit
}