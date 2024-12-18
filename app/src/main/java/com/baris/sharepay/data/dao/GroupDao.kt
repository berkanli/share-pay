package com.baris.sharepay.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.baris.sharepay.data.model.Group

@Dao
interface GroupDao {
    @Insert
    suspend fun insert(group: Group)

    @Query("SELECT * FROM groups")
    suspend fun getAllGroups(): List<Group>

    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupById(groupId: String): Group?

    @Query("DELETE FROM groups WHERE id = :groupId")
    suspend fun deleteGroupById(groupId: String)

    @Update
    suspend fun updateGroup(group: Group)
}