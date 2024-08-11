package org.example.project

import android.os.Build
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val homeScreenPadding: Dp = 20.dp
}

actual fun getPlatform(): Platform = AndroidPlatform()