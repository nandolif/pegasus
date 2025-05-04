package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.domain.entities.Bank

class CreateBank(
    private val repository: BankRepository,
) : Usecase<Bank, Unit>, Subject<CreateBank> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Bank) {
        notifyAll(ObserverEvents.CREATE_BANK, repository.create(input))
    }
}