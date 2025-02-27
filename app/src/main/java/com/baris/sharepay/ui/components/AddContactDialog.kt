package com.baris.sharepay.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*


@Composable
fun AddContactDialog(onDismiss: (String) -> Unit) {
    var friendId by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss("") },
        confirmButton = {
            TextButton(onClick = { onDismiss(friendId) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss("") }) {
                Text("Cancel")
            }
        },
        title = { Text("Add New Friend") },
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = friendId,
                    onValueChange = { friendId = it },
                    label = { Text("Friend ID") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}