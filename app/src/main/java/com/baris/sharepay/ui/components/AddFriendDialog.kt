package com.baris.sharepay.ui.components

import android.content.ContentResolver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.baris.sharepay.ui.viewmodels.UserViewModel


@Composable
fun AddFriendDialog(
    onDismiss: () -> Unit,
    onAddFriend: (User) -> Unit,
    userViewModel: UserViewModel,
    currentUserId: String
) {
    var selectedFriend by remember { mutableStateOf<User?>(null) }
    val friends by userViewModel.users.collectAsState(initial = emptyList())
    //val currentUser by remember { mutableStateOf(userViewModel.getUserById(currentUserId)) }

    LaunchedEffect(currentUserId) {
        userViewModel.fetchAllUsers()
    }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Select a Friend")
            LazyColumn {
                items(friends.filter { it.id != currentUserId }) { friend ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedFriend = friend }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFriend == friend,
                            onClick = { selectedFriend = friend }
                        )
                        Text(text = friend.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                selectedFriend?.let { onAddFriend(it) }
            }) {
                Text(text = "Add Friend")
            }
        }
    }
}