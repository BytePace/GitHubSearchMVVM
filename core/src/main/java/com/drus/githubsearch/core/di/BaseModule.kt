package com.drus.githubsearch.core.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule(
    private val application: Application,
) {

    @Provides
    @Singleton
    fun provideContext(): Context = application
}