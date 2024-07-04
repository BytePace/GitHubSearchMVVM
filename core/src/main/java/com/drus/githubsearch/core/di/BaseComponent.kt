package com.drus.githubsearch.core.di

import com.drus.githubsearch.core.utils.AppScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@AppScope
@Component(modules = [BaseModule::class, NetworkModule::class, NavigationModule::class])
interface BaseComponent: BaseDependencies {
    override val router: Router
    override val retrofit: Retrofit
    override val navigationHolder: NavigatorHolder
    override val okHttpClient: OkHttpClient
}

interface BaseDependencies {
    val router: Router
    val navigationHolder: NavigatorHolder
    val retrofit: Retrofit
    val okHttpClient: OkHttpClient
}