package com.codmine.fatura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.codmine.fatura.components.MainNavigationGraph
import com.codmine.fatura.ui.theme.FaturaTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            FaturaTheme {
                ProvideWindowInsets(
                    windowInsetsAnimationsEnabled = true,
                    consumeWindowInsets = false)
                {
                    MainNavigationGraph()
                }
            }
        }
    }
}