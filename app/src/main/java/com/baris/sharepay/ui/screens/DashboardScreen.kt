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
    var groups by remember { mutableStateOf(listOf<Group>()) }
    var showDialog by remember { mutableStateOf(false) }

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
                GroupCard(group = group, onClick = { navController.navigate("groupDetails/${group.id}") })
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
                    viewModel = groupViewModel,
                    onDismiss = { name, members ->
                        if (name.isNotEmpty() && members.isNotEmpty()) {
                            groupViewModel.addGroup(name, members)
                        }
                        showDialog = false
                    }
                )
            }
        }
    }
}