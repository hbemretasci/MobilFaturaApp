package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.fatura.R
import com.codmine.fatura.components.DateField
import com.codmine.fatura.components.ListField
import com.codmine.fatura.components.NumberFieldDone
import com.codmine.fatura.components.SectionHeader
import com.codmine.fatura.viewmodel.FaturaViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Kalemler(
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
        KalemBilgileri(viewModel, context, focusManager, snackbarHostState ,keyboardController)
        //AlıcıBilgileri(viewModel, context, focusManager, snackbarHostState, keyboardController)
        //IrsaliyeBilgisi(viewModel, context, focusManager)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun KalemBilgileri(
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

    SectionHeader(label = stringResource(id = R.string.label_section4))
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