package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository


class CreateBank(
    private val repository: BankRepository,
) : Usecase<BankEntity, Unit>, Subject<CreateBank> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: BankEntity) {
        notifyAll(ObserverEvents.CREATE_BANK, repository.create(input))
    }
}