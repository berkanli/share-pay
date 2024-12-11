package com.baris.sharepay.ui.components.split_strategy

fun calculateEvenSplit(totalAmount: Double, members: List<String>): Map<String, Double> {
    if (members.isEmpty()) throw IllegalArgumentException("Member list cannot be empty")
    val share = totalAmount / members.size
    return members.associateWith { share }
}

fun calculateExactSplit(expenseDetails: Map<String, Double>): Map<String, Double> {
    val totalPaid = expenseDetails.values.sum()
    if (totalPaid <= 0) throw IllegalArgumentException("Total payment must be greater than zero")
    return expenseDetails
}

fun calculatePercentageSplit(totalAmount: Double, percentages: Map<String, Double>): Map<String, Double> {
    val totalPercentage = percentages.values.sum()
    if (totalPercentage != 100.0) throw IllegalArgumentException("Percentages must sum to 100")
    return percentages.mapValues { entry -> (entry.value / 100) * totalAmount }
}

fun optimizeBalances(balances: Map<String, Double>): List<Pair<String, String>> {
    val positive = balances.filter { it.value > 0 }.toList().toMutableList()
    val negative = balances.filter { it.value < 0 }.toList().toMutableList()

    val transactions = mutableListOf<Pair<String, String>>()
    positive.sortByDescending { it.second }
    negative.sortBy { it.second }

    while (positive.isNotEmpty() && negative.isNotEmpty()) {
        val payer = negative.first()
        val receiver = positive.first()

        val settlement = minOf(-payer.second, receiver.second)
        transactions.add(Pair(payer.first, receiver.first))

        if (settlement == -payer.second) {
            negative.removeAt(0)
        } else {
            negative[0] = payer.first to payer.second + settlement
        }

        if (settlement == receiver.second) {
            positive.removeAt(0)
        } else {
            positive[0] = receiver.first to receiver.second - settlement
        }
    }
    return transactions
}