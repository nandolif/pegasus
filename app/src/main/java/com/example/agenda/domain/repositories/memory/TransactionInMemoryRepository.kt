package com.example.agenda.domain.repositories.memory


import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DayMonthYearObj

class TransactionInMemoryRepository: TransactionRepository {
    private val transactions = mutableListOf<Transaction>()
    override suspend fun create(entity: Transaction) {
        transactions.add(entity)
    }

    override suspend fun update(entity: Transaction) {
        val t  = transactions.first { it.id == entity.id }
        transactions[transactions.indexOf(t)] = entity
    }

    override suspend fun delete(entity: Transaction): Boolean {
        return transactions.remove(entity)
    }

    override suspend fun getAll(): List<Transaction> {
        return emptyList<Transaction>().plus(transactions)
    }

    override suspend fun getById(id: String): Transaction {
        return transactions.first { it.id == id }
    }

    override suspend fun getByBank(bank: Bank): List<Transaction> {
        return transactions.filter { it.bankId == bank.id }
    }

    override suspend fun getByGoal(goal: Goal): List<Transaction> {
        return transactions.filter { it.goalId == goal.id }
    }

    override suspend fun getByDate(date: DayMonthYearObj): List<Transaction> {
        return transactions.filter { it.day == date.day && it.month == date.month && it.year == date.year }
    }

    override suspend fun getNByDate(
        date: DayMonthYearObj,
        quantity: Int,
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObj): List<Transaction> {
        return transactions.filter { it.month == date.month && it.year == date.year }
    }

    override suspend fun getByRecurrence(): List<Transaction> {
        return transactions.filter { it.recurrenceType != null }
    }

    override suspend fun getByRecurrenceId(id: String): List<Transaction> {
        return transactions.filter { it.recurrenceId == id }
    }

    override suspend fun deleteByCategory(id: String) {
        transactions.filter { it.categoryId == id }
    }

}