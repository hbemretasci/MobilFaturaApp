package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.fatura.R
import com.codmine.fatura.components.*
import com.codmine.fatura.util.StringToFloat
import com.codmine.fatura.viewmodel.FaturaViewModel
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    focusManager: FocusManager
)
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
            onFocusedFunction = {
                val text = faturaIrsaliyeNum.text
                faturaIrsaliyeNum = faturaIrsaliyeNum.copy(selection = TextRange(0, text.length))
            },
            onFocusFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequester.bringIntoView()
                }
            },
            onValueChangeFunction = { if (it.text.length <= 25) faturaIrsaliyeNum = it},
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
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

@OptIn(
    ExperimentalComposeUiApi::class,
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
    val bringIntoViewRequesterAdrRow = remember { BringIntoViewRequester() }
    val bringIntoViewRequesterNameRow = remember { BringIntoViewRequester() }

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
        NumberFieldWithError(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaVknTckn,
            errorValue = isErrorVknTckn,
            errorIcon = Icons.Filled.Error,
            errorIconDesc = R.string.cont_vkn_tckn_error,
            label = R.string.label_vkn_tckn,
            onValueChangeFunction = {
                if ((it.text.length <= 11) && (!it.text.contains(",")) && (!it.text.contains("."))) faturaVknTckn = it
                isErrorVknTckn = (it.text.length < 10)
            },
            onNextFunction = {
                keyboardController?.hide()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = faturaVknTckn.text
                faturaVknTckn = faturaVknTckn.copy(selection = TextRange(0, text.length))
            },
            onFocusFunction = { }
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
                .weight(.5f)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                stringResource(id = R.string.button_bilgileri_getir),
                textAlign = TextAlign.Center,
            )
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
            onFocusedFunction = {
                val text = faturaUnvan.text
                faturaUnvan = faturaUnvan.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 35)  faturaUnvan = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = { }
        )
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaVergiDairesi,
            label = R.string.label_vergi_dairesi,
            onFocusedFunction = {
                val text = faturaVergiDairesi.text
                faturaVergiDairesi = faturaVergiDairesi.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 25) faturaVergiDairesi = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = { }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .bringIntoViewRequester(bringIntoViewRequesterNameRow),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaAdi,
            label = R.string.label_adi,
            onFocusedFunction = {
                val text = faturaAdi.text
                faturaAdi = faturaAdi.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 25) faturaAdi = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequesterNameRow.bringIntoView()
                }
            }
        )
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaSoyadi,
            label = R.string.label_soyadi,
            onFocusedFunction = {
                val text = faturaSoyadi.text
                faturaSoyadi = faturaSoyadi.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 25) faturaSoyadi = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequesterNameRow.bringIntoView()
                }
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequesterAdrRow),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CopyField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaAdres,
            label = R.string.label_adres,
            onFocusedFunction = {
                val text = faturaAdres.text
                faturaAdres = faturaAdres.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 75) faturaAdres = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = {
                scope.launch {
                    delay(300)
                    bringIntoViewRequesterAdrRow.bringIntoView()
                }
            }
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
    var selectedFaturaTipi by remember { viewModel.selectedFaturaTipi }
    val faturaParaBirimiList = remember { viewModel.faturaParaBirimiList }
    var expandedParaBirimiList by remember { mutableStateOf(false) }
    var selectedParaBirimi by remember { viewModel.selectedParaBirimi }
    var isDovizKuruEnabled by remember { mutableStateOf(false) }
    var faturaDovizKuru by remember { viewModel.faturaDovizKuru }

    LaunchedEffect(key1 = true) {
        viewModel.initializeBaslikFields()
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
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
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
                if (isDovizKuruEnabled) {
                    scope.launch {
                        delay(300)
                        focusManager.moveFocus(focusDirection = FocusDirection.Next)
                    }
                } else {
                    faturaDovizKuru = TextFieldValue("0.0")
                    focusManager.moveFocus(focusDirection = FocusDirection.Down)
                }
            }
        )

        /*
        scope.launch {
            snackbarHostState.showSnackbar(
                message = "Kur bilgisi 0.0 olarak alındı.",
                duration = SnackbarDuration.Short)
        }
         */

        NumberField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            fieldValue = faturaDovizKuru,
            enabled = isDovizKuruEnabled,
            label = R.string.label_doviz_kuru,
            onValueChangeFunction = {
                if (it.text.length <= 8 && !it.text.contains(",")) faturaDovizKuru = it },
            onNextFunction = {
                keyboardController?.hide()
                faturaDovizKuru = TextFieldValue(StringToFloat(faturaDovizKuru.text).toString())
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = faturaDovizKuru.text
                faturaDovizKuru = faturaDovizKuru.copy(selection = TextRange(0, text.length))
            },
            onFocusFunction = { }
        )
    }
}