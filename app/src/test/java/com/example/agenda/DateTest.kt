package com.example.agenda

import com.example.agenda.app.common.EventType
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.repositories.memory.BankInMemoryRepository
import com.example.agenda.domain.repositories.memory.EventInMemoryRepository
import com.example.agenda.domain.repositories.memory.GoalInMemoryRepository
import com.example.agenda.domain.repositories.memory.TransactionInMemoryRepository
import com.example.agenda.domain.usecases.Backup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test


class DateTest {

    @OptIn(ExperimentalCoroutinesApi::class)

    @Test
    fun `test entity values`() {

        val bankRepository = BankInMemoryRepository()
        val eventRepository = EventInMemoryRepository()
        val transactionRepository = TransactionInMemoryRepository()
        val goalRepository = GoalInMemoryRepository()

        runBlocking {
            bankRepository.create(
                Bank(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    name = "Banco do Brasil",
                    balance = 10f,
                )
            )
            eventRepository.create(
                Event(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    day = 1,
                    month = 12,
                    year = 2001,
                    description = "Sexo",
                    recurrenceType = null,
                    nDays = null,
                    nWeeks = null,
                    nMonths = null,
                    nYears = null,
                    recurrenceId = null,
                    eventType = EventType.REMINDER,
                )
            )
            transactionRepository.create(
                Transaction(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    amount = 10f,
                    day = 1,
                    month = 12,
                    year = 2001,
                    description = "Sexo",
                    bankId = "aaaaaaaaaaa",
                    recurrenceId = null,
                    ghost = false,
                    goalId = null,
                    canceled = false,
                    canceledDay = null,
                    canceledMonth = null,
                    canceledYear = null,
                    nDays = null,
                    nWeeks = null,
                    nMonths = null,
                    nYears = null,
                    recurrenceType = null,
                )
            )
            goalRepository.create(
                Goal(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    achieved = false,
                    amount = 10f,
                    title = "Sexo",
                    actualAmount = null
                )
            )
            goalRepository.create(
                Goal(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    achieved = false,
                    amount = 10f,
                    title = "Sexo",
                    actualAmount = null
                )
            )
            goalRepository.create(
                Goal(
                    id = null,
                    created_at = null,
                    updated_at = null,
                    achieved = false,
                    amount = 10f,
                    title = "Sexo",
                    actualAmount = null
                )
            )
        }

        val backup = Backup(
            bankRepository = bankRepository,
            eventRepository = eventRepository,
            transactionRepository = transactionRepository,
            goalRepository = goalRepository
        )


        runBlocking {
            backup.execute(Unit)
        }
    }
}

