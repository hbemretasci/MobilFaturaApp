package com.codmine.fatura.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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
            label = { Text(text = stringResource(id = label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
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
    enabled : Boolean,
    fieldValue : TextFieldValue,
    label : Int,
    onValueChangeFunction : (TextFieldValue) -> Unit,
    onNextFunction : () -> Unit,
    onFocusedFunction : () -> Unit,
    onFocusFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) onFocusedFunction() else onNextFunction()
            }
            .onFocusEvent {
                if (it.isFocused) onFocusFunction()
            },
        enabled = enabled,
        value = fieldValue,
        onValueChange = onValueChangeFunction,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onNextFunction() }),
        label = { Text (stringResource(id = label)) }
    )
}

@Composable
fun NumberFieldWithError(
    modifier : Modifier,
    fieldValue : TextFieldValue,
    errorValue : Boolean,
    errorIcon : ImageVector,
    errorIconDesc : Int,
    label : Int,
    onValueChangeFunction : (TextFieldValue) -> Unit,
    onNextFunction : () -> Unit,
    onFocusedFunction : () -> Unit,
    onFocusFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) onFocusedFunction() else onNextFunction()
            }
            .onFocusEvent {
                if (it.isFocused) onFocusFunction()
            },
        value = fieldValue,
        onValueChange = onValueChangeFunction,
        trailingIcon = {
            if (errorValue) Icon(errorIcon,
                stringResource(id = errorIconDesc),
                tint = androidx.compose.material3.MaterialTheme.colorScheme.error) },
        isError = errorValue,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onNextFunction() }),
        label = { Text (stringResource(id = label)) }
    )
}

@Composable
fun CopyField(
    modifier : Modifier,
    fieldValue : TextFieldValue,
    label : Int,
    onValueChangeFunction : (TextFieldValue) -> Unit,
    onNextFunction : () -> Unit,
    onFocusedFunction : () -> Unit,
    onFocusFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) onFocusedFunction() else onNextFunction()
            }
            .onFocusEvent {
                if (it.isFocused) onFocusFunction()
            },
        value = fieldValue,
        onValueChange = onValueChangeFunction,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onNextFunction() }),
        singleLine = true,
        label = { Text (stringResource(id = label)) }
    )
}

@Composable
fun CopyFieldSmall(
    modifier : Modifier,
    fieldValue : TextFieldValue,
    label : Int,
    onValueChangeFunction : (TextFieldValue) -> Unit,
    onNextFunction : () -> Unit,
    onFocusedFunction : () -> Unit,
    onFocusFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) onFocusedFunction() else onNextFunction()
            }
            .onFocusEvent {
                if (it.isFocused) onFocusFunction()
            },
        value = fieldValue,
        textStyle = MaterialTheme.typography.bodySmall,
        onValueChange = onValueChangeFunction,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onNextFunction() }),
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberFieldSmall(
    modifier : Modifier,
    readOnly : Boolean,
    enabled : Boolean,
    fieldValue : TextFieldValue,
    label : Int,
    onValueChangeFunction : (TextFieldValue) -> Unit,
    onNextFunction : () -> Unit,
    onFocusedFunction : () -> Unit,
    onFocusFunction : () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) onFocusedFunction() else onNextFunction()
            }
            .onFocusEvent {
                if (it.isFocused) onFocusFunction()
            },
        readOnly = readOnly,
        enabled = enabled,
        value = fieldValue,
        textStyle = MaterialTheme.typography.bodySmall,
        onValueChange = onValueChangeFunction,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onNextFunction() }),
        label = {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListFieldSmall(
    modifier : Modifier,
    expandedStatus : Boolean,
    fieldValue : String,
    label : @Composable (() -> Unit)? = null,
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
        OutlinedTextField(
            value = fieldValue,
            textStyle = MaterialTheme.typography.bodySmall,
            readOnly = true,
            onValueChange = { },
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus)
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