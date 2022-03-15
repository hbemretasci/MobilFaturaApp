package com.codmine.fatura.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codmine.fatura.view.LoginScreen
import com.codmine.fatura.view.MainScreen

sealed class Screen (val route: String) {
    object Login: Screen("login_route")
    object Main: Screen("main_route/{gib}/{vk}/{pass}")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route)
    {
        composable(Screen.Login.route) { LoginScreen(navController = navController) }

        composable(Screen.Main.route, arguments = listOf(
            navArgument("gib") { type = NavType.StringType },
            navArgument("vk") { type = NavType.StringType },
            navArgument("pass") { type = NavType.StringType })
        ) {
            val gibNo = remember { it.arguments?.getString("gib") }
            val vkNo = remember { it.arguments?.getString("vk") }
            val passText = remember { it.arguments?.getString("pass") }
            MainScreen(gibNo!!, vkNo!!, passText!!)
        }
    }
}