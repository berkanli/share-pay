package com.baris.sharepay.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.ui.viewmodels.GroupViewModel

@Composable
fun CreateGroupDialog(
    viewModel: GroupViewModel,
    onDismiss: (String, List<String>) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var memberList by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Create Group",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = memberList,
                onValueChange = { memberList = it },
                label = { Text("Members (comma-separated)") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    if (groupName.isEmpty()) {
                        errorMessage = "Group Name cannot be empty"
                        return@Button
                    }
                    if (memberList.isEmpty()) {
                        errorMessage = "At least one member is required"
                        return@Button
                    }
                    val members = memberList.split(",").map { it.trim() }
//                    val group = Group(
//                        id = java.util.UUID.randomUUID().toString(),
//                        name = groupName,
//                        members = members
//                    )
//                    viewModel.addGroup(groupName, members)
                    errorMessage = ""
                    onDismiss(groupName, members)
                }) {
                    Text("Create")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onDismiss("", emptyList()) }) {
                    Text("Cancel")
                }
            }
        }
    }
}