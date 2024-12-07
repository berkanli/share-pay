package com.baris.sharepay.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DisplaySplitsScreen(splits: Map<String, Double>) {
    LazyColumn {
        items(splits.entries.toList()) { (member, amount) ->
            Text(
                text = "$member owes $amount",
                onTextLayout = {} // Explicitly provide onTextLayout
            )
        }
    }
}

fun calculateSplits(
    amount: Double,
    members: List<String>,
    splitMethod: String
): Map<String, Double> {
    return when (splitMethod) {
        "equal" -> {
            val splitAmount = amount / members.size
            members.associateWith { splitAmount }
        }

        "percentages" -> {
            // Example: Custom logic where members supply their percentages
            // Replace with your dynamic input handling
            mapOf("user1@example.com" to amount * 0.4, "user2@example.com" to amount * 0.6)
        }

        else -> emptyMap()
    }
}
