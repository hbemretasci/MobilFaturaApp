package com.codmine.fatura.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.codmine.fatura.R
import com.codmine.fatura.components.Screen
import com.codmine.fatura.viewmodel.FaturaViewModel

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: FaturaViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    //viewModel
    //val userAuthentication = remember { viewModel.userAuthentication }
    //val errorMessage = remember { viewModel.errorMessage }
    val isLoading = remember { viewModel.isLoading }

    //screen
    var txtKullanici by remember { mutableStateOf("") }
    var txtSifre by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        txtKullanici = viewModel.customDataStore.getKullaniciData(context)
        txtSifre = viewModel.customDataStore.getSifreData(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.screen_logo),
                contentDescription = stringResource(id = R.string.cont_logo)
            )
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            OutLinedTextFieldNext(txtKullanici, stringResource(R.string.login_1_label), focusManager) {
                if (it.length <= 10) txtKullanici = it }
            OutLinedTextFieldDone(txtSifre, stringResource(R.string.login_2_label),
                function = { if (it.length <= 10) txtSifre = it },
                onDoneFunction = {
                    keyboardController?.hide()
                    scope.launch {
                        /*
                        viewModel.loadUserData(txtKullanici, txtSifre)
                        if (!isLoading.value) {
                            if (errorMessage.value.isNotEmpty()) {
                                snackbarHostState.showSnackbar("Bağlantı Hatası!")
                            } else {
                                if(userAuthentication.value.loginResult == QUERY_RESULT_KULLANICI_DOGRU) {
                                    viewModel.customDataStore.saveKullaniciSifreData(context, txtKullanici, txtSifre)
                                    navController.navigate("main_route/${txtKullanici}/${txtSifre}") {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar(userAuthentication.value.loginMessage)
                                }
                            }
                        }
                         */
                        if (txtKullanici.isEmpty()) txtKullanici = "123"
                        if (txtSifre.isEmpty()) txtSifre = "abc"
                        viewModel.customDataStore.saveKullaniciSifreData(context, txtKullanici, txtSifre)
                        navController.navigate("main_route/999/${txtKullanici}/${txtSifre}") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }

                    }
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Button(onClick = {
                scope.launch {
                    /*
                    viewModel.loadUserData(txtGibKodu, txtVkNo, txtSifre)
                    if (!isLoading.value) {
                        if (errorMessage.value.isNotEmpty()) {
                            snackbarHostState.showSnackbar("Bağlantı Hatası!")
                        } else {
                            if(userAuthentication.value.loginResult == QUERY_RESULT_KULLANICI_DOGRU) {
                                viewModel.customDataStore.saveLoginData(context, true)
                                viewModel.customDataStore.saveMukellefData(context, txtGibKodu, txtVkNo, txtSifre)
                                navController.navigate("main_route/${txtGibKodu}/${txtVkNo}/${txtSifre}") {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            } else {
                                snackbarHostState.showSnackbar(userAuthentication.value.loginMessage)
                            }
                        }
                    }
                     */
                    if (txtKullanici.isEmpty()) txtKullanici = "123"
                    if (txtSifre.isEmpty()) txtSifre = "abc"
                    viewModel.customDataStore.saveKullaniciSifreData(context, txtKullanici, txtSifre)
                    navController.navigate("main_route/999/${txtKullanici}/${txtSifre}") {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }

                }
            },
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.button_giris),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 14.dp))
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun OutLinedTextFieldNext(text: String,
                          label: String,
                          focusManager : FocusManager,
                          function: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        value = text,
        onValueChange = function,
        maxLines = 1,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }),
        label = { Text(label) }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutLinedTextFieldDone(text: String,
                          label: String,
                          function: (String) -> Unit,
                          onDoneFunction : () -> Unit) {

    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp),
        value = text,
        onValueChange = function,
        maxLines = 1,
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDoneFunction() }),
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = icon, contentDescription = stringResource(id = R.string.cont_pass))
            }
        }
    )
}