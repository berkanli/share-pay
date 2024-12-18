package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.sharepay.data.dao.ExpenseDao
import com.baris.sharepay.data.model.Expense
import com.baris.sharepay.ui.components.split_strategy.calculateEvenSplit
import com.baris.sharepay.ui.components.split_strategy.calculateExactSplit
import com.baris.sharepay.ui.components.split_strategy.calculatePercentageSplit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

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
            fetchExpensesByGroup(groupId)
        }
    }

    fun fetchExpensesByGroup(groupId: String) {
        viewModelScope.launch {
            val expenses = expenseDao.getExpensesByGroup(groupId)
            _expenses.value = expenses
        }
    }

    fun splitExpense(totalAmount: Double, members: List<String>, method: String): Map<String, Double> {
        return when (method) {
            "even" -> calculateEvenSplit(totalAmount, members)
            "exact" -> calculateExactSplit(members.associateWith { totalAmount / members.size })
            "percentage" -> calculatePercentageSplit(totalAmount, members.associateWith { 100.0 / members.size })
            else -> throw IllegalArgumentException("Invalid split method")
        }
    }

    private fun calculateEvenSplit(totalAmount: Double, members: List<String>): Map<String, Double> {
        val share = totalAmount / members.size
        return members.associateWith { share }
    }

    private fun calculateExactSplit(details: Map<String, Double>): Map<String, Double> {
        return details
    }

    private fun calculatePercentageSplit(totalAmount: Double, percentages: Map<String, Double>): Map<String, Double> {
        return percentages.mapValues { (_, percentage) -> totalAmount * (percentage / 100.0) }
    }
}