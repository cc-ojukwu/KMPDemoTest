package org.example.project

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val homeScreenPadding: Dp = 20.dp
}

actual fun getPlatform(): Platform = IOSPlatform()