package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.repositories.TransactionRepository

class GetAllTransactions(
    private val transactionRepository: TransactionRepository
): Usecase<Unit, Unit>, Subject<GetAllTransactions> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit) {
        notifyAll(ObserverEvents.GET_ALL_TRANSACTIONS,transactionRepository.getAll().reversed().toMutableList())
    }
}