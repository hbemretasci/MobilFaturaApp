package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.fatura.R
import com.codmine.fatura.components.*
import com.codmine.fatura.util.ThousandSeparatorVisualTransformation
import com.codmine.fatura.util.stringToFloat
import com.codmine.fatura.util.textToTextField
import com.codmine.fatura.viewmodel.FaturaViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding

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
        KalemlerScreen(viewModel, context, focusManager, snackbarHostState ,keyboardController)
        //KalemBilgileri(viewModel, context, focusManager, snackbarHostState ,keyboardController)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KalemlerScreen(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController?)
{
    ExtendedFloatingActionButton(
        onClick = { /* do something */ },
        icon = { Icon(Icons.Filled.Add, "Localized description") },
        text = { Text(text = "Extended FAB") },
    )

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
    val kalemBirimList = remember { viewModel.kalemBirimList }
    var expandedBirimList by remember { mutableStateOf(false) }
    var selectedBirim by remember { mutableStateOf(kalemBirimList[0]) }
    var kalemBirimFiyat by remember { viewModel.kalemBirimFiyat }
    var kalemIskontoOrani by remember { viewModel.kalemIskontoOrani }
    var kalemIskontoTutari by remember { viewModel.kalemIskontoTutari }
    var kalemMalHizmetTutari by remember { viewModel.kalemMalHizmetTutari }
    val kalemKDVOraniList = remember { viewModel.kalemKDVOraniList }
    var expandedKDVOrani by remember { mutableStateOf(false) }
    var selectedKDV by remember { viewModel.selectedKDV }
    var kalemKDVTutari by remember { viewModel.kalemKDVTutari }

    LaunchedEffect(key1 = true) {
        //viewModel.initializeKalemlerFields()
    }

    SectionHeader(label = stringResource(id = R.string.label_section4))
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
    ) {
        CopyFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.48f)
                .padding(start = 6.dp),
            fieldValue = kalemMalHizmet,
            label = R.string.label_mal_hizmet,
            onFocusedFunction = {
                val text = kalemMalHizmet.text
                kalemMalHizmet = kalemMalHizmet.copy(selection = TextRange(0, text.length))
            },
            onValueChangeFunction = { if (it.text.length <= 25) kalemMalHizmet = it },
            onNextFunction = { focusManager.moveFocus(focusDirection = FocusDirection.Next) },
            onFocusFunction = { }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.22f)
                .padding(start = 6.dp),
            fieldValue = kalemMiktar,
            readOnly = false,
            enabled = true,
            label = R.string.label_miktar,
            onValueChangeFunction = {
                if (it.text.length <= 4 && !it.text.contains(",")) kalemMiktar = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemMiktar = textToTextField(kalemMiktar.text)
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = kalemMiktar.text
                kalemMiktar = kalemMiktar.copy(
                    selection = TextRange(0, text.length)
                )
            },
            onFocusFunction = { }
        )
        ListFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.3f)
                .padding(start = 6.dp),
            expandedStatus = expandedBirimList,
            fieldValue = selectedBirim,
            label = {
                Text(
                    text = stringResource(id = R.string.label_birim),
                    style = MaterialTheme.typography.bodySmall
                )
            },
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
    Spacer(modifier = Modifier.padding(vertical = 2.dp))
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
    ) {
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.35f)
                .padding(start = 6.dp),
            fieldValue = kalemBirimFiyat,
            readOnly = false,
            enabled = true,
            label = R.string.label_birim_fiyat,
            onValueChangeFunction = {
                if (it.text.length <= 5 && !it.text.contains(",")) kalemBirimFiyat = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemBirimFiyat = textToTextField(kalemBirimFiyat.text)
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = kalemBirimFiyat.text
                kalemBirimFiyat = kalemBirimFiyat.copy(
                    selection = TextRange(0, text.length)
                )
            },
            onFocusFunction = { }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.3f)
                .padding(start = 6.dp),
            fieldValue = kalemIskontoOrani,
            readOnly = false,
            enabled = true,
            label = R.string.label_iskonto_orani,
            onValueChangeFunction = {
                if (it.text.length <= 5 && !it.text.contains(",") && stringToFloat(it.text) in 0.0..100.0) kalemIskontoOrani = it
            },
            onNextFunction = {
                keyboardController?.hide()
                kalemIskontoOrani = textToTextField(kalemIskontoOrani.text)
                viewModel.updateIskontoOraniValue()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = kalemIskontoOrani.text
                kalemIskontoOrani = kalemIskontoOrani.copy(
                    selection = TextRange(0, text.length)
                )
            },
            onFocusFunction = { }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.35f)
                .padding(start = 6.dp),
            fieldValue = kalemIskontoTutari,
            readOnly = false,
            enabled = true,
            label = R.string.label_iskonto_tutari,
            onValueChangeFunction = {
                if (it.text.length <= 6 && !it.text.contains(",")) kalemIskontoTutari = it },
            onNextFunction = {
                keyboardController?.hide()
                kalemIskontoTutari = textToTextField(kalemIskontoTutari.text)
                viewModel.updateIskontoTutariValue()
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            },
            onFocusedFunction = {
                val text = kalemIskontoTutari.text
                kalemIskontoTutari = kalemIskontoTutari.copy(
                    selection = TextRange(0, text.length)
                )
            },
            onFocusFunction = { }
        )
    }
    Spacer(modifier = Modifier.padding(vertical = 2.dp))
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
    ) {
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.35f)
                .padding(start = 6.dp),
            fieldValue = kalemMalHizmetTutari,
            readOnly = true,
            enabled = true,
            label = R.string.label_mal_hizmet_tutari,
            visualTransformation = ThousandSeparatorVisualTransformation(),
            onValueChangeFunction = { },
            onNextFunction = { },
            onFocusedFunction = { },
            onFocusFunction = { }
        )
        ListFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.3f)
                .padding(start = 6.dp),
            expandedStatus = expandedKDVOrani,
            fieldValue = selectedKDV,
            label = {
                Text(
                    text = stringResource(id = R.string.label_kdv_orani),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            list = kalemKDVOraniList,
            onExpandedChangeFunction = { expandedKDVOrani = !expandedKDVOrani },
            onDismissRequestFunction = { expandedKDVOrani = false },
            onClickFunction = {
                selectedKDV = it
                expandedKDVOrani = false
                viewModel.updateFaturaKalemValues()
                focusManager.moveFocus(focusDirection = FocusDirection.Next)
            }
        )
        NumberFieldSmall(
            modifier = Modifier
                .height(56.dp)
                .weight(.35f)
                .padding(start = 6.dp),
            fieldValue = kalemKDVTutari,
            readOnly = true,
            enabled = true,
            label = R.string.label_kdv_tutari,
            visualTransformation = ThousandSeparatorVisualTransformation(),
            onValueChangeFunction = { },
            onNextFunction = { },
            onFocusedFunction = { },
            onFocusFunction = { }
        )
    }
}