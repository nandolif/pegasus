package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.repositories.TransactionRepository

class UpdateTransaction(private val repository: TransactionRepository): Usecase<TransactionEntity, Unit>, Subject<UpdateTransaction> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: TransactionEntity) {
        repository.update(input)
        notifyAll(ObserverEvents.UPDATE_TRANSACTION, input)
    }

}