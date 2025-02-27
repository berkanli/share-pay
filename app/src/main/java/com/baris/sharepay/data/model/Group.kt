package com.baris.sharepay.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "groups")
@TypeConverters(Converters::class)
data class Group(
    @PrimaryKey val id: String,
    val name: String,
    val members: List<User>
)