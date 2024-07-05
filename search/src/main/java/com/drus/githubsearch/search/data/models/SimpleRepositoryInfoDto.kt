package com.drus.githubsearch.search.data.models

import com.google.gson.annotations.SerializedName

data class SimpleRepositoryInfoDto(
    @SerializedName("name")
    val repositoryName: String,
    @SerializedName("url")
    val repositoryURL: String,
    @SerializedName("owner")
    val repositoryOwner: RepositoryOwnerDto,
    @SerializedName("pushed_at")
    val date: String,
)