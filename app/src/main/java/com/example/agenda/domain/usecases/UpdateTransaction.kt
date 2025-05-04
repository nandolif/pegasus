package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Transaction

class UpdateTransaction(private val repository: TransactionRepository): Usecase<Transaction, Unit>, Subject<UpdateTransaction> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Transaction) {
        repository.update(input)
        notifyAll(ObserverEvents.UPDATE_TRANSACTION, input)
    }

}