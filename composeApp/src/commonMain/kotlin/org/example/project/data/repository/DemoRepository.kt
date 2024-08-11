package org.example.project.data.repository

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.model.MovieCollectionResponse
import org.example.project.model.MovieResponse
import org.example.project.utils.Result


interface DemoRepository {
    suspend fun getMovieCollection(page: Int): Result<MovieCollectionResponse>
    suspend fun getMovie(id: Int): Result<MovieResponse>
}

class DemoRepositoryImpl(
    private val client: HttpClient,
): DemoRepository {
    private val log = Logger.withTag(DemoRepositoryImpl::class.simpleName!!)

    override suspend fun getMovieCollection(page: Int): Result<MovieCollectionResponse> = try {
        val response = client.get("discover/movie?&page=$page").body<MovieCollectionResponse>()
        Result.Success(response)
    } catch (e: Exception) {
        log.e(e) { "Error fetching movie collection" }
        Result.Error(e)
    }

    override suspend fun getMovie(id: Int): Result<MovieResponse> = try {
        val response = client.get("movie/$id").body<MovieResponse>()
        Result.Success(response)
    } catch (e: Exception) {
        log.e(e) { "Error fetching movie with id: $id" }
        Result.Error(e)
    }
}