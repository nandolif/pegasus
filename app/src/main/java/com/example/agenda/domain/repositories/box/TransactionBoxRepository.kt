package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Transaction
import io.objectbox.Box

class TransactionBoxRepository(
    private val box: Box<Transaction>,
): TransactionRepository {
    override suspend fun getByBank(bank: BankEntity): List<TransactionEntity> {
        return box.all.filter { it.bankId == bank.id }
    }

    override suspend fun getByGoal(goal: GoalEntity): List<TransactionEntity> {
        return box.all.filter { it.goalId == goal.id }
    }

    override suspend fun getByDate(date: DayMonthYearObject): List<TransactionEntity> {
        return box.all.filter { it.day == date.day && it.month == date.month && it.year == date.year }
    }

    override suspend fun getNByDate(
        date: DayMonthYearObject,
        quantity: Int,
    ): List<TransactionEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObject): List<TransactionEntity> {
        return box.all.filter { it.month == date.month && it.year == date.year }
    }

    override suspend fun create(entity: TransactionEntity) {
        box.put(entity as Transaction)
    }

    override suspend fun update(entity: TransactionEntity) {
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
                updated_at = App.Time.now()
            ))
        }
    }

    override suspend fun delete(entity: TransactionEntity): Boolean {
        box.all.first { it.id == entity.id }.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<TransactionEntity> {
        return box.all
    }

    override suspend fun getById(id: String): TransactionEntity {
        return box.all.first { it.id == id }
    }

    override suspend fun getByRecurrence(): List<TransactionEntity> {
        return box.all.filter { it.recurrenceType != null }
    }

    override suspend fun getByRecurrenceId(id: String): List<TransactionEntity> {
        return box.all.filter { it.recurrenceId == id }
    }

    override suspend fun deleteByCategory(id: String) {
        box.all.filter { it.categoryId == id }.forEach {
            box.remove(it)
        }

    }
}