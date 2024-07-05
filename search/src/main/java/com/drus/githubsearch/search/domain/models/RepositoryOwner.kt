package com.drus.githubsearch.search.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryOwner(
    val userName: String,
): Parcelable
