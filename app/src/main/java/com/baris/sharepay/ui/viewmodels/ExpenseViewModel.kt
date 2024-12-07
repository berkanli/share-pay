package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.sharepay.data.dao.ExpenseDao
import com.baris.sharepay.data.model.Expense
import kotlinx.coroutines.launch
import java.util.UUID

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {

    fun addExpense(groupId: String, name: String, amount: Double, splitMethod: String, details: Map<String, Double>) {
        val expense = Expense(
            id = UUID.randomUUID().toString(),
            groupId = groupId,
            name = name,
            amount = amount,
            splitMethod = splitMethod,
            details = details
        )
        viewModelScope.launch {
            expenseDao.insert(expense)
        }
    }
}