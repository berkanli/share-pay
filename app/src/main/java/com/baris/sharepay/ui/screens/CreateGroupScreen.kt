package com.baris.sharepay.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baris.sharepay.data.model.User
import com.baris.sharepay.ui.viewmodels.GroupViewModel

@Composable
fun CreateGroupScreen(viewModel: GroupViewModel, onNavigate: (String) -> Unit, currentUser: User) {
    var groupName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Group Name", onTextLayout = {}) },
            interactionSource = remember { MutableInteractionSource() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
        Button(onClick = {
            if (groupName.isEmpty()) {
                errorMessage = "Group Name cannot be empty"
                return@Button
            }
            val id = viewModel.addGroup(groupName, currentUser)
            errorMessage = ""
            onNavigate(id)
        }) {
            Text("Create Group")
        }
    }
}