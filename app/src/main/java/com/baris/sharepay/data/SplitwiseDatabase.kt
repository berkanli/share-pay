package com.baris.sharepay.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baris.sharepay.data.dao.ExpenseDao
import com.baris.sharepay.data.dao.GroupDao
import com.baris.sharepay.data.dao.UserDao
import com.baris.sharepay.data.model.Converters
import com.baris.sharepay.data.model.Expense
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.data.model.User

@Database(entities = [Group::class, Expense::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class SplitwiseDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao
}