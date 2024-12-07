package com.baris.sharepay

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baris.sharepay.ui.screens.CreateGroupScreen
import com.baris.sharepay.ui.viewmodels.GroupViewModel
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
}