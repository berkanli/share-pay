package com.baris.sharepay.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.baris.sharepay.data.model.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses WHERE groupId = :groupId")
    suspend fun getExpensesByGroup(groupId: String): List<Expense>

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>
}