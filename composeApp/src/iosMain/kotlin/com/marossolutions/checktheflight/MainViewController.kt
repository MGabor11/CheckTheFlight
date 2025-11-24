package com.marossolutions.checktheflight

import androidx.compose.ui.window.ComposeUIViewController
import com.marossolutions.checktheflight.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()


    }
) { App() }