package com.drus.githubsearch.search.data.models

import com.google.gson.annotations.SerializedName

data class RepositoryInfoListDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val list: List<SimpleRepositoryInfoDto>
)