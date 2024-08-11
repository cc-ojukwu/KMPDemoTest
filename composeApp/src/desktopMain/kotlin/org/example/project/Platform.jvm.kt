package org.example.project

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val homeScreenPadding: Dp = 48.dp

}

actual fun getPlatform(): Platform = JVMPlatform()