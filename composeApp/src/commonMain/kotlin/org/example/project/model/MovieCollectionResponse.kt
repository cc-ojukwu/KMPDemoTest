package org.example.project.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCollectionResponse(
    val page: Int,
    val results: List<MovieResponse>
)
