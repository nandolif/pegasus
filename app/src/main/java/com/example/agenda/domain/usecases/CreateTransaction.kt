package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.domain.entities.Transaction

class CreateTransaction(
    private val repository: TransactionRepository,
) : Usecase<TransactionEntity, Unit>, Subject<CreateTransaction> {
    override val observers: MutableList<Observer> = mutableListOf()

    override suspend fun execute(input: TransactionEntity) {
        val transaction = Transaction(
            day = input.day,
            month = input.month,
            year = input.year,
            amount = input.amount,
            description = input.description,
            bankId = input.bankId,
            goalId = input.goalId,
            recurrenceId = input.recurrenceId,
            ghost = input.ghost,
            id = null,
            created_at = null,
            updated_at = null,
            canceled = input.canceled,
            canceledDay = input.canceledDay,
            canceledMonth = input.canceledMonth,
            canceledYear = input.canceledYear,
            nDays = input.nDays,
            nWeeks = input.nWeeks,
            nMonths = input.nMonths,
            nYears = input.nYears,
            recurrenceType = input.recurrenceType,
            categoryId = input.categoryId,
        )

        notifyAll(ObserverEvents.CREATE_TRANSACTION,repository.create(transaction))
    }


}