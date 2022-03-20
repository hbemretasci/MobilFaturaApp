package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.fatura.R
import com.codmine.fatura.components.*
import com.codmine.fatura.viewmodel.FaturaViewModel
import com.google.accompanist.insets.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FaturaOlusturScreen(gibNo : String,
                        vkNo : String,
                        passText : String,
                        snackbarHostState: SnackbarHostState) {

    val pagerState = rememberPagerState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        FaturaOlusturTabs(pagerState = pagerState)
        HorizontalPager(count = faturaOlusturTabItems.size, state = pagerState) { page ->
            when (page) {
                0 -> Baslik(snackbarHostState, context)
                1 -> Kalemler(snackbarHostState, context)
                2 -> Toplam()
            }
        }
    }
}

@Composable
fun Toplam() {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Text("Toplam")
    }
}