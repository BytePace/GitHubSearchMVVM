package com.drus.githubsearch.search.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleRepositoryInfo(
    val repositoryName: String,
    val repositoryURL: String,
    val repositoryOwner: RepositoryOwner,
    val date: String,
): Parcelable
