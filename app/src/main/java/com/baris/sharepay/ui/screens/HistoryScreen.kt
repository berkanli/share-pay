package com.baris.sharepay.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.baris.sharepay.data.model.Expense

@Composable
fun HistoryScreen(expenses: List<Expense>) {
    LazyColumn {
        items(expenses) { expense ->
            Text("${expense.name}: ${expense.amount}", onTextLayout = {})
        }
    }
}