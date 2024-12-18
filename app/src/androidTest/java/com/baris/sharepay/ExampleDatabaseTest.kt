package com.baris.sharepay

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baris.sharepay.data.SplitwiseDatabase
import com.baris.sharepay.data.dao.ExpenseDao
import com.baris.sharepay.data.dao.GroupDao
import com.baris.sharepay.data.model.Expense
import com.baris.sharepay.data.model.Group
import com.baris.sharepay.data.model.User
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleDatabaseTest {
    private lateinit var database: SplitwiseDatabase
    private lateinit var groupDao: GroupDao
    private lateinit var expenseDao: ExpenseDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, SplitwiseDatabase::class.java).build()
        groupDao = database.groupDao()
        expenseDao = database.expenseDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndFetchGroup() = runBlocking {
        val group = Group(id = "1", name = "Trip", members = listOf<User>())
        groupDao.insert(group)

        val groups = groupDao.getAllGroups()
        assertThat(groups.size, `is`(1))
        assertThat(groups[0].name, `is`("Trip"))
    }

    @Test
    fun testInsertAndFetchExpense() = runBlocking {
        val expense = Expense(
            id = "1",
            groupId = "1",
            name = "Lunch",
            amount = 100.0,
            splitMethod = "EQUAL",
            details = mapOf("Alice" to 50.0, "Bob" to 50.0)
        )
        expenseDao.insert(expense)

        val expenses = expenseDao.getExpensesByGroup("1")
        assertThat(expenses.size, `is`(1))
        assertThat(expenses[0].name, `is`("Lunch"))
    }
}