package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.appModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }
    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KMPDemoTest",
        ) {
            App()
        }
    }
}