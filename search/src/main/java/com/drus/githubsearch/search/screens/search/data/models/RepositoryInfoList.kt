package com.drus.githubsearch.search.screens.search.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryInfoList(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val list: List<SimpleRepositoryInfo>
): Parcelable