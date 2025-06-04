package com.example.agenda.domain.usecases

import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.domain.entities.Bank

class GetAllBanks(
    private val bankRepository: BankRepository
): Usecase<Unit, List<Bank>>, Subject<GetAllBanks>{
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit): List<Bank> {
        val allBanks = bankRepository.getAll()
        val banks = mutableListOf<Bank>()
        for (bank in allBanks) {
            banks.add(App.UseCases.getBank.execute(bank.id!!))
        }
        return banks
    }
}