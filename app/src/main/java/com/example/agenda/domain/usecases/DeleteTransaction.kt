package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.repositories.TransactionRepository

class DeleteTransaction(
    private val transactionRepository: TransactionRepository
): Usecase<TransactionEntity, Unit>, Subject<DeleteTransaction> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: TransactionEntity) {
        transactionRepository.delete(input)
        notifyAll(ObserverEvents.DELETE_TRANSCATION, Unit)
    }
}