package com.drus.githubsearch.search.data.models

import com.google.gson.annotations.SerializedName

data class RepositoryDetailsDto(
    @SerializedName("commit")
    val commitDto: CommitDto
)
data class CommitDto(
    @SerializedName("commit")
    val details: CommitDetailsDto
)

data class CommitDetailsDto(
    @SerializedName("author")
    val author: CommitAuthorDto
)

data class CommitAuthorDto(
    @SerializedName("date")
    val date: String
)