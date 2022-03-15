package com.codmine.fatura.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
