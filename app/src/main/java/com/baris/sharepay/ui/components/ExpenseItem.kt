package com.baris.sharepay.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baris.sharepay.data.model.Expense

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Name: ${expense.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Amount: ${expense.amount}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Split Method: ${expense.splitMethod}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Details: ${expense.details}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}