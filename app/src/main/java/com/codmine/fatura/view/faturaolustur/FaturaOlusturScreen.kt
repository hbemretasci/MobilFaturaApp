package com.codmine.fatura.view.faturaolustur

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.fatura.R
import com.codmine.fatura.components.SectionHeader
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
                        paddingValues : PaddingValues) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            //.systemBarsPadding()
            .padding(paddingValues)
            //.statusBarsPadding()
            //.imePadding()
    ) {
        FaturaOlusturTabs(pagerState = pagerState)
        HorizontalPager(count = faturaOlusturTabItems.size, state = pagerState) { page ->
            faturaOlusturTabItems[page].screen()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.material.ExperimentalMaterialApi::class,
    com.google.accompanist.insets.ExperimentalAnimatedInsets::class
)
@Composable
fun Baslik(viewModel: FaturaViewModel = hiltViewModel()) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        //.padding(bottom = 50.dp)
        //.paddingFromBaseline(bottom = 20.dp)
        //.imePadding()
        .verticalScroll(scrollState)
    )
    {
        FaturaBilgileri(viewModel, context, focusManager, keyboardController)
        AlıcıBilgileri(viewModel, context, focusManager)
        IrsaliyeBilgisi(viewModel, context, focusManager)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IrsaliyeBilgisi(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager)
{
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()
    var faturaIrsaliyeNum by remember { viewModel.faturaIrsaliyeNum }
    val faturaIrsaliyeDate by remember { viewModel.faturaIrsaliyeDate }

    SectionHeader(label = stringResource(id = R.string.label_section3))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = faturaIrsaliyeNum,
            onValueChange = { faturaIrsaliyeNum = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_irsaliye_numarasi)) },
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaIrsaliyeDate,
            readOnly = true,
            onValueChange = { },
            label = { Text (stringResource(id = R.string.label_irsaliye_tarihi))},
            trailingIcon = {
                IconButton(onClick = { viewModel.selectDate(context) }) {
                    Icon(imageVector = Icons.Filled.CalendarToday,
                        contentDescription = stringResource(id = R.string.cont_textfield_irsaliye_date)
                    )
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            value = faturaIrsaliyeNum,
            onValueChange = { faturaIrsaliyeNum = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_irsaliye_numarasi)) },
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaIrsaliyeDate,
            readOnly = true,
            onValueChange = { },
            label = { Text (stringResource(id = R.string.label_irsaliye_tarihi))},
            trailingIcon = {
                IconButton(onClick = { viewModel.selectDate(context) }) {
                    Icon(imageVector = Icons.Filled.CalendarToday,
                        contentDescription = stringResource(id = R.string.cont_textfield_irsaliye_date)
                    )
                }
            }
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlıcıBilgileri(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager)
{
    val scope = rememberCoroutineScope()

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
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.6f)
                .padding(horizontal = 12.dp),
            value = faturaVknTckn,
            onValueChange = {
                if (it.length <= 11) faturaVknTckn = it
                isErrorVknTckn = (it.length < 10)
                            },
            trailingIcon = {
                if (isErrorVknTckn) Icon(
                    Icons.Filled.Error,
                    stringResource(id = R.string.cont_vkn_tckn_error),
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.error) },
            isError = isErrorVknTckn,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            label = { Text (stringResource(id = R.string.label_vkn_tckn))},
        )
        OutlinedButton(
            onClick = {
            /* Bilgileri Getir */
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
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaUnvan,
            onValueChange = { faturaUnvan = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_unvan))},
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaVergiDairesi,
            onValueChange = { faturaVergiDairesi = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_vergi_dairesi))},
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaAdi,
            onValueChange = { faturaAdi = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_adi)) },
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaSoyadi,
            onValueChange = { faturaSoyadi = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_soyadi)) },
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            value = faturaAdres,
            onValueChange = { faturaAdres = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
            singleLine = true,
            label = { Text (stringResource(id = R.string.label_adres)) },
        )
    }

}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun FaturaBilgileri(
    viewModel: FaturaViewModel,
    context: Context,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?)
{
    val faturaDate by remember { viewModel.faturaDate }
    val faturaTime by remember { viewModel.faturaTime }

    val faturaTipiList = remember { viewModel.faturaTipiList }
    var expandedFaturaTipiList by remember { mutableStateOf(false) }
    var selectedFaturaTipi by remember { mutableStateOf(faturaTipiList[0]) }

    val faturaParaBirimiList = remember { viewModel.faturaParaBirimiList }
    var expandedParaBirimiList by remember { mutableStateOf(false) }
    var selectedParaBirimi by remember { mutableStateOf(faturaParaBirimiList[0]) }

    var faturaDovizKuru by remember { mutableStateOf("0.00") }

    LaunchedEffect(key1 = true) {
        viewModel.getCurrentDateAndTime()
    }

    SectionHeader(label = stringResource(id = R.string.label_section1))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaDate,
            readOnly = true,
            onValueChange = { },
            label = { Text (stringResource(id = R.string.label_duzenlenme_tarihi))},
            trailingIcon = {
                IconButton(onClick = { viewModel.selectDate(context) }) {
                    Icon(imageVector = Icons.Filled.CalendarToday,
                        contentDescription = stringResource(id = R.string.cont_textfield_date)
                    )
                }
            }
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .weight(.5f)
                .padding(vertical = 6.dp, horizontal = 12.dp),
            expanded = expandedFaturaTipiList,
            onExpandedChange = {
                expandedFaturaTipiList = !expandedFaturaTipiList
            }
        ) {
            TextField(
                value = selectedFaturaTipi,
                readOnly = true,
                onValueChange = { },
                label = { Text(stringResource(id = R.string.label_fatura_tipi)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedFaturaTipiList
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expandedFaturaTipiList,
                onDismissRequest = {
                    expandedFaturaTipiList = false
                }
            ) {
                faturaTipiList.forEach { selectionFaturaTipi ->
                    DropdownMenuItem(
                        text = { Text(text = selectionFaturaTipi) },
                        onClick = {
                            selectedFaturaTipi = selectionFaturaTipi
                            expandedFaturaTipiList = false
                        }
                    )
                }
            }
        }
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.5f)
                .padding(horizontal = 12.dp),
            value = faturaDovizKuru,
            onValueChange = { if (it.length <= 8) faturaDovizKuru = it },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            label = { Text (stringResource(id = R.string.label_doviz_kuru))}
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .weight(.5f)
                .padding(vertical = 6.dp, horizontal = 12.dp),
            expanded = expandedParaBirimiList,
            onExpandedChange = {
                expandedParaBirimiList = !expandedParaBirimiList
            }
        ) {
            TextField(
                value = selectedParaBirimi,
                readOnly = true,
                onValueChange = { },
                label = { Text(stringResource(id = R.string.label_para_birimi)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedParaBirimiList
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expandedParaBirimiList,
                onDismissRequest = {
                    expandedParaBirimiList = false
                }
            ) {
                faturaParaBirimiList.forEach { selectionParaBirimi ->
                    DropdownMenuItem(
                        text = { Text(text = selectionParaBirimi) },
                        onClick = {
                            selectedParaBirimi = selectionParaBirimi
                            expandedParaBirimiList = false
                        }
                    )
                }
            }
        }
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