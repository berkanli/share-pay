package com.baris.sharepay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baris.sharepay.data.DatabaseProvider
import com.baris.sharepay.ui.screens.AddExpenseScreen
import com.baris.sharepay.ui.screens.ContactsScreen
import com.baris.sharepay.ui.screens.CreateGroupScreen
import com.baris.sharepay.ui.screens.DashboardScreen
import com.baris.sharepay.ui.screens.GroupDetailsScreen
import com.baris.sharepay.ui.screens.SettingsScreen
import com.baris.sharepay.ui.viewmodels.ExpenseViewModel
import com.baris.sharepay.ui.viewmodels.GroupViewModel
import com.baris.sharepay.ui.viewmodels.UserViewModel
import com.baris.sharepay.ui.viewmodels.ViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController, currentUserId: String) {
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val viewModelFactory = ViewModelFactory(database)

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            val groupViewModel: GroupViewModel = viewModel(factory = viewModelFactory)
            val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
            DashboardScreen(userViewModel, groupViewModel, navController, currentUserId)
        }
//        composable("createGroup") {
//            val groupViewModel: GroupViewModel = viewModel(factory = viewModelFactory)
//
//            CreateGroupScreen(groupViewModel) { groupId ->
//                navController.navigate("addExpense/$groupId")
//            }
//        }

        composable("settings") {
            SettingsScreen(navController)
        }
        composable("groupDetails/{groupId}") { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            val groupViewModel: GroupViewModel = viewModel(factory = viewModelFactory)
            val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
            val expenseViewModel: ExpenseViewModel = viewModel(factory = viewModelFactory)
            GroupDetailsScreen(
                groupId = groupId,
                groupViewModel = groupViewModel,
                expenseViewModel = expenseViewModel,
                navController = navController,
                userViewModel = userViewModel,
                currentUserId = currentUserId
            )
        }
        composable(
            route = "addExpense/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            val expenseViewModel: ExpenseViewModel = viewModel(factory = viewModelFactory)

            groupId?.let {
                AddExpenseScreen(expenseViewModel, groupId = it)
            }
        }
        composable("contacts/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
            ContactsScreen(userViewModel = userViewModel, currentUserId = userId)
        }
    }
}