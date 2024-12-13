package com.baris.sharepay.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.ui.components.CreateGroupDialog
import com.baris.sharepay.ui.components.GroupCard
import com.baris.sharepay.ui.viewmodels.GroupViewModel

@Composable
fun DashboardScreen(groupViewModel: GroupViewModel, navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    val groups by groupViewModel.groups.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        groupViewModel.fetchAllGroups()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(groups) { group ->
                GroupCard(
                    group = group,
                    onClick = { navController.navigate("groupDetails/${group.id}") },
                    onLongClick = {
                        selectedGroup = group
                        showDeleteDialog = true
                    }
                )
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
            Icon(Icons.Default.Add, contentDescription = "Add Group")
        }
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                CreateGroupDialog(
                    onDismiss = { name, members ->
                        if (name.isNotEmpty() && members.isNotEmpty()) {
                            groupViewModel.addGroup(name, members)
                        }
                        showDialog = false
                    }
                )
            }
        }
        if (showDeleteDialog && selectedGroup != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Group") },
                text = { Text("Are you sure you want to delete ${selectedGroup?.name}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedGroup?.let { group ->
                                groupViewModel.deleteGroup(group)
                            }
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}