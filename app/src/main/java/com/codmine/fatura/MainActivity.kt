package com.codmine.fatura

import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethod
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.codmine.fatura.components.MainNavigationGraph
import com.codmine.fatura.ui.theme.FaturaTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            FaturaTheme {

                val navController = rememberNavController()
                MainNavigationGraph(navController = navController)


                /*
                ProvideWindowInsets(
                    windowInsetsAnimationsEnabled = false,
                    consumeWindowInsets = false
                ) {
                    val navController = rememberNavController()
                    MainNavigationGraph(navController = navController)
                }
                 */
            }
        }
    }
}