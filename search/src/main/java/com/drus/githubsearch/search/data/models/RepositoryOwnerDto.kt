package com.drus.githubsearch.search.data.models

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerDto(
    @SerializedName("login")
    val userName: String
)