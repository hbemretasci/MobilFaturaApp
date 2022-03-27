package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
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
import com.codmine.fatura.components.*
import com.codmine.fatura.util.StringToFloat
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
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    androidx.compose.material3.ExperimentalMaterial3Api::class
)
@Composable
fun KalemBilgileri(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController?)
{
    val scope = rememberCoroutineScope()

    //val bringIntoViewRequesterAdrRow = remember { BringIntoViewRequester() }
    //val bringIntoViewRequesterNameRow = remember { BringIntoViewRequester() }

    var kalemMalHizmet by remember { viewModel.kalemMalHizmet }
    var kalemMiktar by remember { viewModel.kalemMiktar }
    val kalemBirimList = listOf("Adet", "Paket", "Kutu", "kg", "lt", "ton")
    var expandedBirimList by remember { mutableStateOf(false) }
    var selectedBirim by remember { mutableStateOf(kalemBirimList[0]) }
    var kalemBirimFiyat by remember { viewModel.kalemBirimFiyat }
    var kalemIskontoOrani by remember { viewModel.kalemIskontoOrani }
    var kalemIskontoTutari by remember { viewModel.kalemIskontoTutari }
    var kalemMalHizmetTutari by remember { viewModel.kalemMalHizmetTutari }
    val kalemKDVOraniList = listOf("0", "1", "8", "18")
    var expandedKDVOrani by remember { mutableStateOf(false) }
    var selectedKDV by remember { mutableStateOf(kalemKDVOraniList[0]) }
    var kalemKDVTutari by remember { viewModel.kalemKDVTutari }

    LaunchedEffect(key1 = true) {
        viewModel.initializeKalemlerFields()
    }

    SectionHeader(label = stringResource(id = R.string.label_section4))

    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
    ) {
        CopyFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.5f)
                .padding(start = 6.dp),
            fieldValue = kalemMalHizmet,
            label = R.string.label_mal_hizmet,
            onFocusedFunction = { },
            onValueChangeFunction = { if (it.length <= 25) kalemMalHizmet = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(start = 6.dp),
            fieldValue = kalemMiktar,
            readOnly = false,
            enabled = true,
            label = R.string.label_miktar,
            onValueChangeFunction = {
                if (it.length <= 5 && !it.contains(",")) kalemMiktar = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemMiktar = StringToFloat(kalemMiktar).toString()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
        ListFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(vertical = 6.dp, horizontal = 6.dp),
            expandedStatus = expandedBirimList,
            fieldValue = selectedBirim,
            list = kalemBirimList,
            onExpandedChangeFunction = { expandedBirimList = !expandedBirimList },
            onDismissRequestFunction = { expandedBirimList = false },
            onClickFunction = {
                selectedBirim = it
                expandedBirimList = false
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
    }

    Spacer(modifier = Modifier.padding(vertical = 6.dp))

    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
    ) {
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(start = 6.dp),
            fieldValue = kalemBirimFiyat,
            readOnly = false,
            enabled = true,
            label = R.string.label_birim_fiyat,
            onValueChangeFunction = {
                if (it.length <= 5 && !it.contains(",")) kalemBirimFiyat = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemBirimFiyat = StringToFloat(kalemBirimFiyat).toString()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(start = 6.dp),
            fieldValue = kalemIskontoOrani,
            readOnly = false,
            enabled = true,
            label = R.string.label_iskonto_orani,
            onValueChangeFunction = {
                if (it.length <= 4 && !it.contains(",")) kalemIskontoOrani = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemIskontoOrani = StringToFloat(kalemIskontoOrani).toString()
                viewModel.updateIskontoOraniValue()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(start = 6.dp),
            fieldValue = kalemIskontoTutari,
            readOnly = false,
            enabled = true,
            label = R.string.label_iskonto_tutari,
            onValueChangeFunction = {
                if (it.length <= 6 && !it.contains(",")) kalemIskontoTutari = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemIskontoTutari = StringToFloat(kalemIskontoTutari).toString()
                viewModel.updateIskontoTutariValue()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.25f)
                .padding(start = 6.dp),
            fieldValue = kalemMalHizmetTutari,
            readOnly = true,
            enabled = true,
            label = R.string.label_mal_hizmet_tutari,
            onValueChangeFunction = { },
            onNextFunction = { }
        )
    }

}