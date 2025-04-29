package com.example.agenda

import com.example.agenda.app.common.EventType
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.repositories.memory.BankInMemoryRepository
import com.example.agenda.domain.repositories.memory.EventInMemoryRepository
import com.example.agenda.domain.repositories.memory.GoalInMemoryRepository
import com.example.agenda.domain.repositories.memory.TransactionInMemoryRepository
import com.example.agenda.domain.usecases.Backup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class DateTest {

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

    @Test
    fun `date difference text`(){
        val firstDay = DayMonthYearObj(1,12,2001)
        val oneDayLeft = DayMonthYearObj(20,12,2001)
        val eventDay = DayMonthYearObj(21,12,2001)

        var start = DayMonthYearObj(1,1,2001)
        var end = DayMonthYearObj(31,12,2003)
        println(Date.difference(start,end))

        Assert.assertEquals(20,Date.difference(firstDay, eventDay))
        Assert.assertEquals(1,Date.difference(oneDayLeft, eventDay))


        Assert.assertEquals("2 anos",Date.between(start, end))

        start = DayMonthYearObj(1,1,2001)
        end = DayMonthYearObj(1,1,2001)
        Assert.assertEquals("Hoje",Date.between(start, end))

        start = DayMonthYearObj(1,1,2001)
        end = DayMonthYearObj(2,1,2001)
        Assert.assertEquals("1 dia",Date.between(start, end))


        start = DayMonthYearObj(1,1,2001)
        end = DayMonthYearObj(1,2,2001)
        Assert.assertEquals("1 mês",Date.between(start, end))

        start = DayMonthYearObj(1,1,2001)
        end = DayMonthYearObj(15,1,2001)
        Assert.assertEquals("2 semanas",Date.between(start, end))

    }
}

