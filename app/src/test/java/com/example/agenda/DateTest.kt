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
import org.junit.Assert.assertEquals
import org.junit.Test


class DateTest {
    @Test
    fun test (){
    }
}

