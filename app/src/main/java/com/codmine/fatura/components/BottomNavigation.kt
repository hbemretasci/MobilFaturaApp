package com.codmine.fatura.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codmine.fatura.R
import com.codmine.fatura.view.faturaolustur.FaturaOlusturScreen
import com.google.accompanist.insets.navigationBarsPadding

sealed class BottomScreen (val label: Int, val route: String, val icon: ImageVector, val cont:Int) {
    object FaturaOlustur: BottomScreen(R.string.bottom_label_fatura, "fatura_route", Icons.Filled.Edit, R.string.cont_fatura)
    object DuzenlenenBelgeler: BottomScreen(R.string.bottom_label_belgeler, "belgeler_route", Icons.Filled.Description, R.string.cont_belgeler)
    object IptalItirazTalepleri: BottomScreen(R.string.bottom_label_iptal, "iptal_route", Icons.Filled.PendingActions, R.string.cont_iptal)
}

val bottomNavigationItems = listOf(
    BottomScreen.FaturaOlustur,
    BottomScreen.DuzenlenenBelgeler,
    BottomScreen.IptalItirazTalepleri
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigationScreen(navController: NavController) {
    AnimatedVisibility (
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            modifier = Modifier
                .navigationBarsPadding()
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavigationItems.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = stringResource(id = screen.cont)) },
                    label = { Text(text = stringResource(id = screen.label), fontWeight = FontWeight.Bold) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationGraph(gibNo : String,
                          vkNo : String,
                          passText : String,
                          bottomNavController: NavHostController,
                          paddingValues : PaddingValues) {

    NavHost(navController = bottomNavController, startDestination = BottomScreen.FaturaOlustur.route) {
        composable(BottomScreen.FaturaOlustur.route) {
            FaturaOlusturScreen(gibNo, vkNo, passText, paddingValues)
        }
        composable(BottomScreen.DuzenlenenBelgeler.route) {
            //EvraklarScreen(gibNo, vkNo, passText, user, maliMusavir, paddingValues)
        }
        composable(BottomScreen.IptalItirazTalepleri.route) {
            //CariScreen(gibNo, vkNo, passText, user, maliMusavir, paddingValues)
        }

    }

}