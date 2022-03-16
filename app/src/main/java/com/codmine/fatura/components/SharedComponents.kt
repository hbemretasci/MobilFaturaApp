package com.codmine.fatura.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.codmine.fatura.R

@Composable
fun SectionHeader(label: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        SectionHeaderLine()
        androidx.compose.material.Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        SectionHeaderLine()
    }
}

@Composable
private fun RowScope.SectionHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.inversePrimary
    )
}

@Composable
fun DateField(
    modifier : Modifier,
    fieldValue : String,
    label : Int,
    icon : ImageVector,
    iconDesc : Int,
    onSelectFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = fieldValue,
        readOnly = true,
        onValueChange = { },
        label = { Text (stringResource(id = label)) },
        trailingIcon = {
            IconButton(onClick = { onSelectFunction() }) {
                Icon(imageVector = icon,
                    contentDescription = stringResource(id = iconDesc)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListField(
    modifier : Modifier,
    expandedStatus : Boolean,
    fieldValue : String,
    label : Int,
    list : List<String>,
    onExpandedChangeFunction : () -> Unit,
    onDismissRequestFunction : () -> Unit,
    onClickFunction : (String) -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expandedStatus,
        onExpandedChange = { onExpandedChangeFunction() }
    ) {
        TextField(
            value = fieldValue,
            readOnly = true,
            onValueChange = { },
            label = { Text(stringResource(id = label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedStatus
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expandedStatus,
            onDismissRequest = { onDismissRequestFunction() }
        ) {
            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = { onClickFunction(it) }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberField(
    modifier : Modifier,
    fieldValue : String,
    label : Int,
    keyboardController : SoftwareKeyboardController?,
    focusManager : FocusManager,
    onValueChangeFunction : (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = fieldValue,
        onValueChange = onValueChangeFunction,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }),
        label = { Text (stringResource(id = label))}
    )
}