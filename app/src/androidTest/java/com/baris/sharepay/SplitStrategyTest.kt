package com.baris.sharepay

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baris.sharepay.ui.components.split_strategy.calculateEvenSplit
import com.baris.sharepay.ui.components.split_strategy.optimizeBalances
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplitStrategyTest {
    @Test
    fun testEvenSplit() {
        val members = listOf("Alice", "Bob", "Charlie")
        val result = calculateEvenSplit(300.0, members)
        assertEquals(mapOf("Alice" to 100.0, "Bob" to 100.0, "Charlie" to 100.0), result)
    }

    @Test
    fun testOptimizeBalances() {
        val balances = mapOf("Alice" to -40.0, "Bob" to 30.0, "Charlie" to 10.0)
        val result = optimizeBalances(balances)
        assertEquals(listOf(Pair("Alice", "Bob"), Pair("Alice", "Charlie")), result)
    }

}