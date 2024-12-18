package com.baris.sharepay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String = "",
    val phone: String = "",
    val friends: List<String> = emptyList()
)