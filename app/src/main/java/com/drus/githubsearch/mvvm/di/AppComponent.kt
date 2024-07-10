package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.di.BaseDependencies
import com.drus.githubsearch.core.di.NavigationModule
import com.drus.githubsearch.core.utils.AppScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@AppScope
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
    ],
    dependencies = [BaseDependencies::class]
)
interface AppComponent : BaseDependencies