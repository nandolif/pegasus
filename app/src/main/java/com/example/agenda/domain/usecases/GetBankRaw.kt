package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.domain.entities.Bank

class GetBankRaw(
    private val bankRepository: BankRepository,
) : Usecase<String, Bank> {
    override suspend fun execute(input: String): Bank {
        return bankRepository.getById(input)!!
    }
}