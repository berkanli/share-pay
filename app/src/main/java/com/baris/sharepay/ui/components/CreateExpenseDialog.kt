package com.baris.sharepay.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CreateExpenseDialog(onDismiss: () -> Unit, onCreateExpense: (ExpenseDetails) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var splitMethod by remember { mutableStateOf("even") }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Expense Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = splitMethod,
                onValueChange = { splitMethod = it },
                label = { Text("Split Method") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val expenseDetails = ExpenseDetails(name, amount.toDouble(), splitMethod)
                onCreateExpense(expenseDetails)
            }) {
                Text(text = "Create Expense")
            }
        }
    }
}