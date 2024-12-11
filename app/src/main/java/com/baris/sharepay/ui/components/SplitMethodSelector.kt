package com.baris.sharepay.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SplitMethodSelector(onMethodSelected: (String) -> Unit) {
    val methods = listOf("Even", "Exact", "Percentage")
    var selectedMethod by remember { mutableStateOf(methods[0]) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { expanded = true }) {
            Text(text = "Select Method")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            methods.forEach { method ->
                DropdownMenuItem(
                    text = { Text(text = method) },
                    onClick = {
                        selectedMethod = method
                        onMethodSelected(method.toLowerCase())
                        expanded = false
                    }
                )
            }
        }
    }
}