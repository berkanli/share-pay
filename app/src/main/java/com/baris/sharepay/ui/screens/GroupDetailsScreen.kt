package com.baris.sharepay.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.ui.components.AddFriendDialog
import com.baris.sharepay.ui.components.CreateExpenseDialog
import com.baris.sharepay.ui.components.ExpenseItem
import com.baris.sharepay.ui.viewmodels.ExpenseViewModel
import com.baris.sharepay.ui.viewmodels.GroupViewModel
import com.baris.sharepay.ui.viewmodels.UserViewModel

@Composable
fun GroupDetailsScreen(
    groupId: String,
    groupViewModel: GroupViewModel,
    expenseViewModel: ExpenseViewModel,
    navController: NavHostController,
    userViewModel: UserViewModel,
    currentUserId: String
) {
    var group by remember { mutableStateOf<Group?>(null) }
    var showAddFriendDialog by remember { mutableStateOf(false) }
    var showCreateExpenseDialog by remember { mutableStateOf(false) }
    val expenses by expenseViewModel.expenses.collectAsState(initial = emptyList())


    LaunchedEffect(groupId) {
        group = groupViewModel.getGroupById(groupId)
        expenseViewModel.fetchExpensesByGroup(groupId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        if (group != null) {
            Text(text = "Group Name: ${group!!.name}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Members:")
            group!!.members.forEach { member ->
                Text(text = member.name, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showAddFriendDialog = true }) {
                Text(text = "Add Friend")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showCreateExpenseDialog = true }) {
                Text(text = "Create Expense")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Expenses:")
            LazyColumn {
                items(expenses) { expense ->
                    ExpenseItem(expense = expense)
                }
            }
        } else {
            Text(text = "Group not found", style = MaterialTheme.typography.headlineSmall, color = Color.Red)
        }
    }

    if (showAddFriendDialog) {
        AddFriendDialog(
            onDismiss = { showAddFriendDialog = false },
            onAddFriend = { friend ->
                groupViewModel.addFriendToGroup(groupId, friend)
                showAddFriendDialog = false
            },
            userViewModel = userViewModel,
            currentUserId = currentUserId
        )
    }

    if (showCreateExpenseDialog) {
        CreateExpenseDialog(
            onDismiss = { showCreateExpenseDialog = false },
            onCreateExpense = { expenseDetails ->
                val splitDetails = expenseViewModel.splitExpense(
                    totalAmount = expenseDetails.amount,
                    members = group!!.members.map { it.name },
                    method = expenseDetails.splitMethod
                )
                expenseViewModel.addExpense(
                    groupId = groupId,
                    name = expenseDetails.name,
                    amount = expenseDetails.amount,
                    splitMethod = expenseDetails.splitMethod,
                    details = splitDetails
                )
                showCreateExpenseDialog = false
            }
        )
    }

}
