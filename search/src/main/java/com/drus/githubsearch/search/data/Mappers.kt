package com.drus.githubsearch.search.data

import com.drus.githubsearch.search.data.models.RepositoryDetailsDto
import com.drus.githubsearch.search.data.models.RepositoryOwnerDto
import com.drus.githubsearch.search.data.models.SimpleRepositoryInfoDto
import com.drus.githubsearch.search.domain.models.RepositoryDetails
import com.drus.githubsearch.search.domain.models.RepositoryOwner
import com.drus.githubsearch.search.domain.models.SimpleRepositoryInfo

fun RepositoryDetailsDto.toDomain(): RepositoryDetails {
    return RepositoryDetails(commitDate = commitDto.details.author.date)
}

fun SimpleRepositoryInfoDto.toDomain(): SimpleRepositoryInfo {
    return SimpleRepositoryInfo(
        repositoryName = repositoryName,
        repositoryURL = repositoryURL,
        repositoryOwner = repositoryOwner.toDomain(),
        date = date,
    )
}

fun RepositoryOwnerDto.toDomain(): RepositoryOwner {
    return RepositoryOwner(userName = userName)
}