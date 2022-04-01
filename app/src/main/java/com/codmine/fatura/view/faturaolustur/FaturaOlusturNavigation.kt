package com.codmine.fatura.view.faturaolustur

import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.codmine.fatura.R
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

typealias ComposableFun = @Composable () -> Unit

sealed class FaturaOlusturTabs (val label: Int, val icon: ImageVector, val cont:Int) {
    object Baslik: FaturaOlusturTabs(R.string.tab_label_baslik, Icons.Filled.AccountBox , R.string.cont_baslik_tabs)
    object Kalemler: FaturaOlusturTabs(R.string.tab_label_kalemler, Icons.Filled.ViewList, R.string.cont_kalemler_tabs)
    //object Toplam: FaturaOlusturTabs(R.string.tab_label_toplam, Icons.Filled.FactCheck, R.string.cont_toplam_tabs)
}

val faturaOlusturTabItems = listOf(
    FaturaOlusturTabs.Baslik,
    FaturaOlusturTabs.Kalemler
    //FaturaOlusturTabs.Toplam
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FaturaOlusturTabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer)
    {
        faturaOlusturTabItems.forEachIndexed { index, tab ->
            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        stringResource(id = tab.label),
                        color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                    )
                       },
                icon = { /* Icon(tab.icon, contentDescription = stringResource(id = tab.cont)) */ }
            )
        }
    }
}