package com.drus.githubsearch.mvvm.di

import com.drus.githubsearch.core.utils.ActivityScope
import com.drus.githubsearch.mvvm.activity.MainActivity
import com.drus.githubsearch.mvvm.activity.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivitiesModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeToMainActivity(): MainActivity
}