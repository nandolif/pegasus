package com.example.agenda.domain.repositories.memory


import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.repositories.TransactionRepository

class TransactionInMemoryRepository: TransactionRepository {
    private val transactions = mutableListOf<TransactionEntity>()
    override suspend fun create(entity: TransactionEntity) {
        transactions.add(entity)
    }

    override suspend fun update(entity: TransactionEntity) {
        val t  = transactions.first { it.id == entity.id }
        transactions[transactions.indexOf(t)] = entity


    }

    override suspend fun delete(entity: TransactionEntity): Boolean {
        return transactions.remove(entity)
    }

    override suspend fun getAll(): List<TransactionEntity> {
        return emptyList<TransactionEntity>().plus(transactions)
    }

    override suspend fun getById(id: String): TransactionEntity {
        return transactions.first { it.id == id }
    }

    override suspend fun getByBank(bank: BankEntity): List<TransactionEntity> {
        return transactions.filter { it.bankId == bank.id }
    }

    override suspend fun getByGoal(goal: GoalEntity): List<TransactionEntity> {
        return transactions.filter { it.goalId == goal.id }
    }

    override suspend fun getByDate(date: DayMonthYearObject): List<TransactionEntity> {
        return transactions.filter { it.day == date.day && it.month == date.month && it.year == date.year }
    }

    override suspend fun getNByDate(
        date: DayMonthYearObject,
        quantity: Int,
    ): List<TransactionEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObject): List<TransactionEntity> {
        return transactions.filter { it.month == date.month && it.year == date.year }
    }

    override suspend fun getByRecurrence(): List<TransactionEntity> {
        return transactions.filter { it.recurrenceType != null }
    }

    override suspend fun getByRecurrenceId(id: String): List<TransactionEntity> {
        return transactions.filter { it.recurrenceId == id }
    }

    override suspend fun deleteByCategory(id: String) {
        transactions.filter { it.categoryId == id }
    }

}