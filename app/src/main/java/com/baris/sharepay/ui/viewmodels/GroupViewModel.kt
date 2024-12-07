package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.sharepay.data.dao.GroupDao
import com.baris.sharepay.data.model.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class GroupViewModel(private val groupDao: GroupDao) : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> get() = _groups

    fun addGroup(name: String, members: List<String>): String {
        val group = Group(
            id = UUID.randomUUID().toString(),
            name = name,
            members = members
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
}