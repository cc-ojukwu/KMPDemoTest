package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.example.project.data.repository.DemoRepository
import org.example.project.data.repository.DemoRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    singleOf(::DemoRepositoryImpl) { bind<DemoRepository>() }
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
            prettyPrint = true
        }
    }
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }
            install(HttpCache)
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path(PATH)
                    parameters.append(KEY, API_KEY)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}

const val HOST = "api.themoviedb.org"
const val PATH = "3/"
const val KEY = "api_key"
const val API_KEY = "51083da7ffb463437438dec557f492d5"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"