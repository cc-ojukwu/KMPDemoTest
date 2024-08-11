package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(appModule)
        }
    }
) { App() }