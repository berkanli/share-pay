package com.baris.sharepay.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.baris.sharepay.data.model.User
import com.baris.sharepay.ui.components.AddContactDialog
import com.baris.sharepay.ui.components.ContactCard
import com.baris.sharepay.ui.viewmodels.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun ContactsScreen(userViewModel: UserViewModel, currentUserId: String) {
    var showDialog by remember { mutableStateOf(false) }
    val users by userViewModel.users.collectAsState(initial = emptyList())
    val currentUser = users.firstOrNull { it.id == currentUserId }
    val gson = Gson()


    LaunchedEffect(Unit) {
        userViewModel.fetchAllUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentUser?.let { user ->
            val friends = gson.fromJson<List<User>>(user.friends, object : TypeToken<List<User>>() {}.type)
            LazyColumn {
                items(friends) { contact ->
                    ContactCard(contact = contact)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Contact")
        }
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                AddContactDialog(
                    onDismiss = { name, email ->
                        if (name.isNotEmpty() && email.isNotEmpty()) {
                            userViewModel.addFriend(currentUserId, name, email)
                        }
                        showDialog = false
                    }
                )
            }
        }
    }
}