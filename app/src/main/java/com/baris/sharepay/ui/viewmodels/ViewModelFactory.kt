package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baris.sharepay.data.SplitwiseDatabase

class ViewModelFactory(private val database: SplitwiseDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GroupViewModel::class.java) -> GroupViewModel(database.groupDao()) as T
            modelClass.isAssignableFrom(ExpenseViewModel::class.java) -> ExpenseViewModel(database.expenseDao()) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(database.userDao()) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}