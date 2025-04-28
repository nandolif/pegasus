package com.example.agenda.domain.usecases

import com.example.agenda.app.common.EventType
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.helps.File
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction

class Backup(
    private val bankRepository: BankRepository,
    private val eventRepository: EventRepository,
    private val transactionRepository: TransactionRepository,
    private val goalRepository: GoalRepository,
) : Usecase<Unit, Unit>, Subject<Backup> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit) {
        File.deleteFolder(File.Path.BACKUP)

        val banks = bankRepository.getAll()
        val events = eventRepository.getAll()
        val transactions = transactionRepository.getAll()
        val goals = goalRepository.getAll()

        val bankLines = File.CSV.entityListToString(banks)
        val eventLines = File.CSV.entityListToString(events)
        val transactionLines = File.CSV.entityListToString(transactions)
        val goalLines = File.CSV.entityListToString(goals)

        if (bankLines != "") {
            File.CSV.create(
                File.CSV.Data(
                    name = File.Filename.BANK_BACKUP.filename,
                    path = File.Path.BACKUP,
                    data = bankLines
                )
            )
        }

        if (eventLines != "") {

            File.CSV.create(
                File.CSV.Data(
                    name = File.Filename.EVENT_BACKUP.filename,
                    path = File.Path.BACKUP,
                    data = eventLines
                )
            )
        }
        if (transactionLines != "") {
            File.CSV.create(
                File.CSV.Data(
                    name = File.Filename.TRANSACTION_BACKUP.filename,
                    path = File.Path.BACKUP,
                    data = transactionLines
                )
            )
        }
        if (goalLines != "") {
            File.CSV.create(
                File.CSV.Data(
                    name = File.Filename.GOAL_BACKUP.filename,
                    path = File.Path.BACKUP,
                    data = goalLines
                )
            )
        }
        notifyAll(ObserverEvents.BACKUP, Unit)
    }

    suspend fun restore() {
        val bankFile = File.CSV.read(File.Filename.BANK_BACKUP.filename, File.Path.BACKUP)
        val eventFile = File.CSV.read(File.Filename.EVENT_BACKUP.filename, File.Path.BACKUP)
        val transactionFile = File.CSV.read(
            File.Filename.TRANSACTION_BACKUP.filename,
            File.Path.BACKUP
        )
        val goalFile = File.CSV.read(
            File.Filename.GOAL_BACKUP.filename,
            File.Path.BACKUP
        )

        if(bankFile == null && eventFile == null && transactionFile == null && goalFile == null){
            return
        }
        deleteAll()
        restoreBank(bankFile)
        restoreEvent(eventFile)
        restoreTransaction(transactionFile)
        restoreGoal(goalFile)
        File.deleteFolder(File.Path.BACKUP)
    }

    private suspend fun restoreEvent(file: File.CSV.CSVFile?) {
        if (file != null) {
            val _id = 0
            val day = 1
            val month = 2
            val year = 3
            val description = 4
            val id = 5
            val created_at = 6
            val updated_at = 7
            val recurrenceType = 8
            val nDays = 9
            val nWeeks = 10
            val nMonths = 11
            val nYears = 12
            val recurrenceId = 13
            val eventType = 14
            for (data in file.data) {
                val event = Event(
                    day = data[day].toInt(),
                    month = data[month].toInt(),
                    year = data[year].toInt(),
                    description = data[description],
                    id = data[id],
                    created_at = data[created_at].toLong(),
                    updated_at = data[updated_at].toLong(),
                    recurrenceType = if (data[recurrenceType] != "null") RECURRENCE.valueOf(data[recurrenceType]) else null,
                    nDays = data[nDays].toIntOrNull(),
                    nWeeks = data[nWeeks].toIntOrNull(),
                    nMonths = data[nMonths].toIntOrNull(),
                    nYears = data[nYears].toIntOrNull(),
                    recurrenceId = if (data[recurrenceId] != "null") data[recurrenceId] else null,
                    eventType = EventType.valueOf(data[eventType])
                )
                eventRepository.create(event)
            }
        }
    }

    private suspend fun restoreBank(file: File.CSV.CSVFile?) {
        //Header order name,balance,id,_id,created_at,updated_at
        if (file != null) {
            val name = 0
            val balance = 1
            val id = 2
            val _id = 3
            val created_at = 4
            val updated_at = 5

            for (data in file.data) {
                val bank = Bank(
                    name = data[name],
                    balance = data[balance].toFloat(),
                    id = data[id],
                    created_at = data[created_at].toLong(),
                    updated_at = data[updated_at].toLong(),
                )
                bankRepository.create(bank)
            }
        }
    }

    private suspend fun restoreTransaction(file: File.CSV.CSVFile?) {
        if (file != null) {
            val _id = 0
            val id = 1
            val day = 2
            val month = 2
            val year = 3
            val amount = 4
            val description = 5
            val created_at = 6
            val updated_at = 7
            val bankId = 8
            val recurrenceId = 9
            val ghost = 10
            val goalId = 11
            val canceled = 12
            val canceledDay = 13
            val canceledMonth = 14
            val canceledYear = 15
            val nDays = 16
            val nWeeks = 17
            val nMonths = 18
            val nYears = 19
            val recurrenceType = 20

            for (data in file.data) {
                val transaction = Transaction(
                    id = data[id],
                    day = data[day].toInt(),
                    month = data[month].toInt(),
                    year = data[year].toInt(),
                    amount = data[amount].toFloat(),
                    description = data[description],
                    created_at = data[created_at].toLong(),
                    updated_at = data[updated_at].toLong(),
                    bankId = data[bankId],
                    recurrenceId = if (data[recurrenceId] != "null") data[recurrenceId] else null,
                    ghost = data[ghost].toBoolean(),
                    goalId = if (data[goalId] != "null") data[goalId] else null,
                    canceled = data[canceled].toBoolean(),
                    canceledDay = data[canceledDay].toIntOrNull(),
                    canceledMonth = data[canceledMonth].toIntOrNull(),
                    canceledYear = data[canceledYear].toIntOrNull(),
                    nDays = data[nDays].toIntOrNull(),
                    nWeeks = data[nWeeks].toIntOrNull(),
                    nMonths = data[nMonths].toIntOrNull(),
                    nYears = data[nYears].toIntOrNull(),
                    recurrenceType = if (data[recurrenceType] != "null") RECURRENCE.valueOf(data[recurrenceType]) else null
                )
                transactionRepository.create(transaction)
            }
        }

    }

    private suspend fun restoreGoal(file: File.CSV.CSVFile?) {
        if (file != null) {
            val _id = 0
            val id = 1
            val created_at = 2
            val updated_at = 3
            val achieved = 4
            val amount = 5
            val title = 6
            val actualAmount = 7

            for (data in file.data) {
                val goal = Goal(
                    id = data[id],
                    created_at = data[created_at].toLong(),
                    updated_at = data[updated_at].toLong(),
                    achieved = data[achieved].toBoolean(),
                    amount = data[amount].toFloat(),
                    title = data[title],
                    actualAmount = null,
                )
                goalRepository.create(goal)
            }
        }
    }

    private suspend fun deleteAll() {
        val banks = bankRepository.getAll()
        val events = eventRepository.getAll()
        val transactions = transactionRepository.getAll()
        val goals = goalRepository.getAll()

        for (bank in banks) {
            bankRepository.delete(bank)
        }
        for (event in events) {
            eventRepository.delete(event)
        }
        for (transaction in transactions) {
            transactionRepository.delete(transaction)
        }
        for (goal in goals) {
            goalRepository.delete(goal)
        }
    }

}