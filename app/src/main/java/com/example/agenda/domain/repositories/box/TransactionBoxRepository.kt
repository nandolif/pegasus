package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DayMonthYearObj
import io.objectbox.Box

class TransactionBoxRepository(
    private val box: Box<Transaction>,
): TransactionRepository {
    override suspend fun getByBank(bank: Bank): List<Transaction> {
        return box.all.filter { it.bankId == bank.id }
    }

    override suspend fun getByGoal(goal: Goal): List<Transaction> {
        return box.all.filter { it.goalId == goal.id }
    }

    override suspend fun getByDate(date: DayMonthYearObj): List<Transaction> {
        return box.all.filter { it.day == date.day && it.month == date.month && it.year == date.year }
    }

    override suspend fun getNByDate(
        date: DayMonthYearObj,
        quantity: Int,
    ): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObj): List<Transaction> {
        return box.all.filter { it.month == date.month && it.year == date.year }
    }

    override suspend fun create(entity: Transaction) {
        box.put(entity as Transaction)
    }

    override suspend fun update(entity: Transaction) {
        box.all.first { it.id == entity.id}.let {
            box.put(it.copy(
                day = entity.day,
                month = entity.month,
                year = entity.year,
                bankId = entity.bankId,
                goalId = entity.goalId,
                description = entity.description,
                amount = entity.amount,
                recurrenceId = entity.recurrenceId,
                recurrenceType = entity.recurrenceType,
                ghost = entity.ghost,
                nDays = entity.nDays,
                nWeeks = entity.nWeeks,
                nMonths = entity.nMonths,
                nYears = entity.nYears,
                canceledYear = entity.canceledYear,
                canceledMonth = entity.canceledMonth,
                canceledDay = entity.canceledDay,
                canceled = entity.canceled,
                categoryId = entity.categoryId,
                updated_at = App.Time.now()
            ))
        }
    }

    override suspend fun delete(entity: Transaction): Boolean {
        box.all.first { it.id == entity.id }.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<Transaction> {
        return box.all
    }

    override suspend fun getById(id: String): Transaction {
        return box.all.first { it.id == id }
    }

    override suspend fun getByRecurrence(): List<Transaction> {
        return box.all.filter { it.recurrenceType != null }
    }

    override suspend fun getByRecurrenceId(id: String): List<Transaction> {
        return box.all.filter { it.recurrenceId == id }
    }

    override suspend fun deleteByCategory(id: String) {
        box.all.filter { it.categoryId == id }.forEach {
            box.remove(it)
        }

    }
}