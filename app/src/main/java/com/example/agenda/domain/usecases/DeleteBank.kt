package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.app.repositories.TransactionRepository

class DeleteBank(
    private val bankRepository: BankRepository,
    private val transactionRepository: TransactionRepository
) : Usecase<BankEntity, Unit>, Subject<DeleteBank> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: BankEntity) {
        val allTransactions = transactionRepository.getByBank(input)

        for (transaction in allTransactions) {
            transactionRepository.delete(transaction)
        }

        bankRepository.delete(input)
        notifyAll(ObserverEvents.DELETE_BANK, input)
    }
}