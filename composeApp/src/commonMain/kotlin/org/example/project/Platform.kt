package org.example.project

import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String
    val homeScreenPadding: Dp
}

expect fun getPlatform(): Platform