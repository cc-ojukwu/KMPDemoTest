package org.example.project.di

import coil3.annotation.ExperimentalCoilApi
import coil3.network.CacheStrategy
import coil3.network.NetworkFetcher
import coil3.network.ktor.asNetworkClient
import io.ktor.client.HttpClient
import org.example.project.ui.HomeViewModel
import org.example.project.ui.MovieViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

@OptIn(ExperimentalCoilApi::class)
val appModule = module {
    includes(networkModule)

    viewModelOf(::HomeViewModel)
    viewModelOf(::MovieViewModel)

    single {
        NetworkFetcher.Factory(
            networkClient = { get<HttpClient>().asNetworkClient() },
            cacheStrategy = { CacheStrategy() }
        )
    }
}