package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository

class UpdateBank(
    private val repository: BankRepository,
) : Usecase<BankEntity, Unit>, Subject<UpdateBank> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: BankEntity) {
        repository.update(input)
        notifyAll(ObserverEvents.UPDATE_BANK, input)
    }
}