package com.baris.sharepay.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.ui.viewmodels.GroupViewModel

@Composable
fun GroupDetailsScreen(groupId: String, groupViewModel: GroupViewModel) {
    var group by remember { mutableStateOf<Group?>(null) }

    LaunchedEffect(groupId) {
        group = groupViewModel.getGroupById(groupId)
    }

    if (group != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Group Name: ${group!!.name}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Members:")
            group!!.members.forEach { member ->
                Text(text = member, style = MaterialTheme.typography.bodyMedium)
            }
        }
    } else {
        Text(text = "Group not found", style = MaterialTheme.typography.headlineSmall, color = Color.Red)
    }
}
