package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository

class GetBankRaw(
    private val bankRepository: BankRepository,
) : Usecase<String, BankEntity> {
    override suspend fun execute(input: String): BankEntity {
        return bankRepository.getById(input)
    }
}