package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.helps.File
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.app.repositories.TransactionRepository

class Backup(
    private val bankRepository: BankRepository,
    private val eventRepository: EventRepository,
    private val transactionRepository: TransactionRepository,
    private val goalRepository: GoalRepository,
) : Usecase<Unit, Unit>,Subject<Backup> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit) {

        val banks = bankRepository.getAll()
        val events = eventRepository.getAll()
        val transactions = transactionRepository.getAll()
        val goals = goalRepository.getAll()

        val bankLines = File.CSV.entityListToString(banks)
        val eventLines = File.CSV.entityListToString(events)
        val transactionLines = File.CSV.entityListToString(transactions)
        val goalLines = File.CSV.entityListToString(goals)

        File.CSV.create(File.CSV.Data(
            name = "bank-backup",
            path = File.Path.DOWNLOADS,
            data = bankLines
        ))
        File.CSV.create(File.CSV.Data(
            name = "event-backup",
            path = File.Path.DOWNLOADS,
            data = eventLines
        ))
        File.CSV.create(File.CSV.Data(
            name = "transaction-backup",
            path = File.Path.DOWNLOADS,
            data = transactionLines
        ))
        File.CSV.create(File.CSV.Data(
            name = "goal-backup",
            path = File.Path.DOWNLOADS,
            data = goalLines
        ))

        notifyAll(ObserverEvents.BACKUP, Unit)
    }

}