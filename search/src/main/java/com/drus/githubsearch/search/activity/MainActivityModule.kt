package com.drus.githubsearch.search.activity

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.drus.githubsearch.core.utils.ActivityScope
import com.drus.githubsearch.core.utils.FragmentScope
import com.drus.githubsearch.core.utils.getSystemService
import com.drus.githubsearch.search.screens.repositoryDetails.RepositoryDetailsFragment
import com.drus.githubsearch.search.screens.repositoryDetails.RepositoryDetailsModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepositoryDetailsModule::class])
    fun contributeToRepositoryDetailsFragment(): RepositoryDetailsFragment

    companion object {
        @Provides
        @ActivityScope
        fun provideInputManager(activity: MainActivity): InputMethodManager {
            return activity.getSystemService<InputMethodManager>(Context.INPUT_METHOD_SERVICE)
        }
    }
}