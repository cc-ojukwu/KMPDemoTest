package org.example.project.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun DemoTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}