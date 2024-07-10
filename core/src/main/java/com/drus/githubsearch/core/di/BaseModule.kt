package com.drus.githubsearch.core.di

import android.app.Application
import android.content.Context
import com.drus.githubsearch.core.utils.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule(private val application: Application) {

    @Provides
    @AppScope
    fun provideContext(): Context = application
}