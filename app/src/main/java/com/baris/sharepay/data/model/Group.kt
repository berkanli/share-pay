package com.baris.sharepay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val id: String,
    val name: String,
    val members: List<String> // Use a custom TypeConverter for lists
)