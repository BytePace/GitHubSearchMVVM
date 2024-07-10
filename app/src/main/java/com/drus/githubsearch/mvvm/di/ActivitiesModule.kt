package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.utils.ActivityScope
import com.drus.githubsearch.mvvm.AppActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivitiesModule {
    @ActivityScope
    @ContributesAndroidInjector()
    fun contributeToAppActivity(): AppActivity
}