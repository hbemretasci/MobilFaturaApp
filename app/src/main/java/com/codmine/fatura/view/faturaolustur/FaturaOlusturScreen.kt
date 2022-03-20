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
                1 -> Kalemler()
                2 -> Toplam()
            }
        }
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimatedInsets::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Baslik(
    snackbarHostState: SnackbarHostState,
    context: Context,
    viewModel: FaturaViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .navigationBarsWithImePadding()
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        FaturaBilgileri(viewModel, context, focusManager, snackbarHostState ,keyboardController)
        AlıcıBilgileri(viewModel, context, focusManager, snackbarHostState, keyboardController)
        IrsaliyeBilgisi(viewModel, context, focusManager)
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun IrsaliyeBilgisi(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager)
{
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var faturaIrsaliyeNum by remember { viewModel.faturaIrsaliyeNum }
    val faturaIrsaliyeDate by remember { viewModel.faturaIrsaliyeDate }

    SectionHeader(label = stringResource(id = R.string.label_section3))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .bringIntoViewRequester(bringIntoViewRequester),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaIrsaliyeNum,
            label = R.string.label_irsaliye_numarasi,
            focusManager = focusManager,
            onFocusedFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequester.bringIntoView()
                }
            },
            onValueChangeFunction = { faturaIrsaliyeNum = it}
        )
        DateField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaIrsaliyeDate,
            label = R.string.label_irsaliye_tarihi,
            icon = Icons.Filled.CalendarToday,
            iconDesc = R.string.cont_textfield_irsaliye_date,
            onSelectFunction = { viewModel.selectDate(context,"IRS") }
        )
    }

    Spacer(modifier = Modifier.padding(vertical = 40.dp))
}

@OptIn(ExperimentalComposeUiApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
fun AlıcıBilgileri(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController?)
{
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var faturaVknTckn by remember { viewModel.faturaVknTckn }
    var isErrorVknTckn by remember { mutableStateOf(false) }
    var faturaAdi by remember { viewModel.faturaAdi }
    var faturaSoyadi by remember { viewModel.faturaSoyadi }
    var faturaUnvan by remember { viewModel.faturaUnvan }
    var faturaVergiDairesi by remember { viewModel.faturaVergiDairesi }
    var faturaAdres by remember { viewModel.faturaAdres }

    SectionHeader(label = stringResource(id = R.string.label_section2))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberFieldDoneWithError(
            modifier = Modifier
                .weight(.6f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaVknTckn,
            errorValue = isErrorVknTckn,
            errorIcon = Icons.Filled.Error,
            errorIconDesc = R.string.cont_vkn_tckn_error,
            label = R.string.label_vkn_tckn,
            onValueChangeFunction = {
                if ((it.length <= 11) && (!it.contains(",")) && (!it.contains("."))) faturaVknTckn = it
                isErrorVknTckn = (it.length < 10)
            },
            onDoneFunction = {
                keyboardController?.hide()
                focusManager.clearFocus()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Mükellef bilgileri getirildi.",
                        duration = SnackbarDuration.Short)
                }
            }
        )
        OutlinedButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Mükellef bilgileri getirildi.",
                        duration = SnackbarDuration.Short)
                }
            },
            modifier = Modifier
                .weight(.4f)
                .padding(horizontal = 12.dp)
        ) {
            Text(stringResource(id = R.string.button_bilgileri_getir))
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaUnvan,
            label = R.string.label_unvan,
            focusManager = focusManager,
            onFocusedFunction = { },
            onValueChangeFunction = { faturaUnvan = it }
        )
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaVergiDairesi,
            label = R.string.label_vergi_dairesi,
            focusManager = focusManager,
            onFocusedFunction = { },
            onValueChangeFunction = { faturaVergiDairesi = it }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaAdi,
            label = R.string.label_adi,
            focusManager = focusManager,
            onFocusedFunction = { },
            onValueChangeFunction = { faturaAdi = it }
        )
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaSoyadi,
            label = R.string.label_soyadi,
            focusManager = focusManager,
            onFocusedFunction = { },
            onValueChangeFunction = { faturaSoyadi = it }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaAdres,
            label = R.string.label_adres,
            focusManager = focusManager,
            onFocusedFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequester.bringIntoView()
                }
            },
            onValueChangeFunction = { faturaAdres = it }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun FaturaBilgileri(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController?)
{
    val scope = rememberCoroutineScope()

    var faturaDate by remember { viewModel.faturaDate }
    var faturaTime by remember { viewModel.faturaTime }

    val faturaTipiList = remember { viewModel.faturaTipiList }
    var expandedFaturaTipiList by remember { mutableStateOf(false) }
    var selectedFaturaTipi by remember { mutableStateOf(faturaTipiList[0]) }

    val faturaParaBirimiList = remember { viewModel.faturaParaBirimiList }
    var expandedParaBirimiList by remember { mutableStateOf(false) }
    var selectedParaBirimi by remember { mutableStateOf(faturaParaBirimiList[0]) }

    var isDovizKuruEnabled by remember { mutableStateOf(false) }
    var faturaDovizKuru by remember { viewModel.faturaDovizKuru }

    LaunchedEffect(key1 = true) {
        viewModel.initializeFieldsValue()
    }

    SectionHeader(label = stringResource(id = R.string.label_section1))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaDate,
            label = R.string.label_duzenlenme_tarihi,
            icon = Icons.Filled.CalendarToday,
            iconDesc = R.string.cont_textfield_date,
            onSelectFunction = { viewModel.selectDate(context,"FAT") }
        )
        ListField(
            modifier = Modifier
                .weight(.5f)
                .padding(vertical = 6.dp, horizontal = 12.dp),
            expandedStatus = expandedFaturaTipiList,
            fieldValue = selectedFaturaTipi,
            label = R.string.label_fatura_tipi,
            list = faturaTipiList,
            onExpandedChangeFunction = { expandedFaturaTipiList = !expandedFaturaTipiList },
            onDismissRequestFunction = { expandedFaturaTipiList = false },
            onClickFunction = {
                selectedFaturaTipi = it
                expandedFaturaTipiList = false
            }
        )
    }
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ListField(
            modifier = Modifier
                .weight(.5f)
                .padding(vertical = 6.dp, horizontal = 12.dp),
            expandedStatus = expandedParaBirimiList,
            fieldValue = selectedParaBirimi,
            label = R.string.label_para_birimi,
            list = faturaParaBirimiList,
            onExpandedChangeFunction = { expandedParaBirimiList = !expandedParaBirimiList },
            onDismissRequestFunction = { expandedParaBirimiList = false },
            onClickFunction = {
                selectedParaBirimi = it
                expandedParaBirimiList = false
                isDovizKuruEnabled = selectedParaBirimi != faturaParaBirimiList[0]
            }
        )
        NumberFieldDone(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaDovizKuru,
            enabled = isDovizKuruEnabled,
            label = R.string.label_doviz_kuru,
            onValueChangeFunction = {
                if (it.length <= 8 && !it.contains(",")) faturaDovizKuru = it },
            onDoneFunction = {
                keyboardController?.hide()
                try {
                    faturaDovizKuru = faturaDovizKuru.toFloat().toString()
                } catch (e: NumberFormatException) {
                    faturaDovizKuru = "0.0"
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Kur bilgisi 0.0 olarak alındı.",
                            duration = SnackbarDuration.Short)
                    }
                }
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
    }
}

@Composable
fun Kalemler() {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Text("Kalemler")
    }
}

@Composable
fun Toplam() {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Text("Toplam")
    }
}