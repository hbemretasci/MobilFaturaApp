package com.codmine.fatura.view

import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.codmine.fatura.components.BottomNavigationGraph
import com.codmine.fatura.components.BottomNavigationScreen
import com.codmine.fatura.viewmodel.FaturaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(gibNo : String, vkNo : String, passText : String) {

    val bottomNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { BottomNavigationScreen(bottomNavController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        BottomNavigationGraph(gibNo, vkNo, passText, bottomNavController, snackbarHostState)
    }
}