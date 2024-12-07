package com.baris.sharepay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey val id: String,
    val groupId: String, // Foreign key to the Group entity
    val name: String,
    val amount: Double,
    val splitMethod: String,
    val details: Map<String, Double> // Use a TypeConverter for maps
)