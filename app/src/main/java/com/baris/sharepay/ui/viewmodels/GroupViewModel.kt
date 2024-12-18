package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.sharepay.data.dao.GroupDao
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GroupViewModel(private val groupDao: GroupDao) : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> get() = _groups

    fun addGroup(name: String, creator: User): String {
        val group = Group(
            id = UUID.randomUUID().toString(),
            name = name,
            members = listOf(creator)
        )
        viewModelScope.launch {
            groupDao.insert(group)
            fetchAllGroups()
        }
        return group.id
    }

    fun fetchAllGroups() {
        viewModelScope.launch {
            _groups.value = groupDao.getAllGroups()
        }
    }

    fun deleteGroup(groupId: String) {
        viewModelScope.launch {
            groupDao.deleteGroupById(groupId)
            fetchAllGroups()
        }
    }

    suspend fun getGroupById(groupId: String): Group? {
        return groupDao.getGroupById(groupId)
    }

    fun addFriendToGroup(groupId: String, friend: User) {
        viewModelScope.launch {
            val group = groupDao.getGroupById(groupId)
            if (group != null) {
                val updatedMembers = group.members.toMutableList().apply {
                    add(friend)
                }
                val updatedGroup = group.copy(members = updatedMembers)
                groupDao.updateGroup(updatedGroup)
                fetchAllGroups()
            }
        }
    }
}