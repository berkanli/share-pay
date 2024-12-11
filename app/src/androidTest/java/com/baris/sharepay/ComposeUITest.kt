package com.baris.sharepay

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baris.sharepay.ui.navigation.AppNavHost
import com.baris.sharepay.ui.screens.CreateGroupScreen
import com.baris.sharepay.ui.viewmodels.GroupViewModel
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class ComposeUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCreateGroupScreen() {
        val mockViewModel = mock(GroupViewModel::class.java)

        composeTestRule.setContent {
            CreateGroupScreen(viewModel = mockViewModel, onNavigate = {})
        }

        composeTestRule.onNodeWithText("Group Name").performTextInput("Trip")
        composeTestRule.onNodeWithText("Members").performTextInput("Alice, Bob")
        composeTestRule.onNodeWithText("Create Group").performClick()

        verify(mockViewModel).addGroup("Trip", listOf("Alice", "Bob"))
    }

    @Test
    fun testNavigationFromCreateGroupToAddExpense() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController)
        }

        composeTestRule.onNodeWithText("Group Name").performTextInput("Trip")
        composeTestRule.onNodeWithText("Members").performTextInput("Alice, Bob")
        composeTestRule.onNodeWithText("Create Group").performClick()

        assertThat(navController.currentDestination?.route, `is`("addExpense"))
    }

    @Test
    fun testGroupCreationValidation() {
        val mockViewModel = mock(GroupViewModel::class.java)

        composeTestRule.setContent {
            CreateGroupScreen(viewModel = mockViewModel, onNavigate = {})
        }

        composeTestRule.onNodeWithText("Create Group").performClick()
        composeTestRule.onNodeWithText("Group Name cannot be empty").assertExists()
    }


}