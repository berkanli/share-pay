package com.baris.sharepay.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.baris.sharepay.ui.viewmodels.ExpenseViewModel

@Composable
fun AddExpenseScreen(viewModel: ExpenseViewModel, groupId: String) {
    var expenseName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("GroupId: $groupId")
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = expenseName,
            onValueChange = { expenseName = it },
            label = { Text("Expense Name", onTextLayout = {}) },
            interactionSource = remember { MutableInteractionSource() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount", onTextLayout = {}) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            interactionSource = remember { MutableInteractionSource() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Details (user:amount, comma-separated)", onTextLayout = {}) },
            interactionSource = remember { MutableInteractionSource() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val parsedDetails = details.split(",").associate {
                    val parts = it.split(":")
                    parts[0].trim() to parts[1].trim().toDouble()
                }
                viewModel.addExpense(
                    groupId,
                    expenseName,
                    amount.toDouble(),
                    "EQUAL",
                    parsedDetails
                )
            },
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Text("Add Expense", onTextLayout = {})
        }
    }
}