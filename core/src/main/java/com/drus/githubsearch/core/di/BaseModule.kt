package com.drus.githubsearch.core.di

import android.app.Application
import android.content.Context
import com.drus.githubsearch.core.utils.AppDispatchers
import com.drus.githubsearch.core.utils.AppDispatchersImpl
import com.drus.githubsearch.core.utils.AppScope
import com.drus.githubsearch.core.utils.ApplicationContext
import com.drus.githubsearch.core.utils.StringProvider
import com.drus.githubsearch.core.utils.StringProviderImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class BaseModule(
    private val application: Application,
) {

    @Provides
    @AppScope
    fun provideContext(): Context = application

    @Provides
    @AppScope
    fun provideStringProvider(context: Context): StringProvider {
        return StringProviderImpl(context)
    }

    @Provides
    @AppScope
    fun provideDispatcher(): AppDispatchers {
        return AppDispatchersImpl(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            unconfined = Dispatchers.Unconfined,
            main = Dispatchers.Main
        )
    }
}