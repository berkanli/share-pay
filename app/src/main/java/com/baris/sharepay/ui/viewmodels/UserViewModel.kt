package com.baris.sharepay.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.sharepay.data.dao.UserDao
import com.baris.sharepay.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users
    val gson = Gson()

    fun addUser(name: String, email: String): String {
        val user = User(
            id = UUID.randomUUID().toString(),
            name = name,
            email = email,
            friends = emptyList() // Initialize with an empty list
        )
        viewModelScope.launch {
            userDao.insert(user)
            fetchAllUsers()
        }
        return user.id
    }

    fun fetchAllUsers() {
        viewModelScope.launch {
            _users.value = userDao.getAllUsers()
        }
    }

    fun addFriend(userId: String, friendId: String) {
        viewModelScope.launch {
            val user = userDao.getUserById(userId)
            user?.let {
                val updatedFriends = it.friends.toMutableList().apply {
                    add(friendId)
                }
                val updatedUser = it.copy(friends = updatedFriends)
                userDao.updateUser(updatedUser)
                fetchAllUsers()
            }
        }
    }

    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }
}