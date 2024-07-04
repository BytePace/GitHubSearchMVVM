package com.drus.githubsearch.search.di

import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit

interface SearchComponentDependencies {
    fun getRetrofit(): Retrofit
    fun getRouter(): Router
}