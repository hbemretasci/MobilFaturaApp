package com.codmine.fatura.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.codmine.fatura.components.BottomNavigationGraph
import com.codmine.fatura.components.BottomNavigationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(gibNo : String, vkNo : String, passText : String) {

    val bottomNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { BottomNavigationScreen(bottomNavController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* do something */ },
                icon = { Icon(Icons.Filled.Add, "Localized description") },
                text = { Text(text = "Extended FAB") }
            )
        }
    ) {
        BottomNavigationGraph(gibNo, vkNo, passText, bottomNavController, snackbarHostState)
    }
}