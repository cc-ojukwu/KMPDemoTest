package org.example.project

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override val homeScreenPadding: Dp = 80.dp
}

actual fun getPlatform(): Platform = WasmPlatform()