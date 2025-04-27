package com.example.agenda.domain.usecases

import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository

class GetAllBanks(
    private val bankRepository: BankRepository
): Usecase<Unit, Unit>, Subject<GetAllBanks>{
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit) {
        val allBanks = bankRepository.getAll()
        val banks = mutableListOf<BankEntity>()
        for (bank in allBanks) {
            banks.add(App.UseCases.getBank.execute(bank.id!!))
        }
        notifyAll(ObserverEvents.GET_ALL_BANKS, banks)
    }
}