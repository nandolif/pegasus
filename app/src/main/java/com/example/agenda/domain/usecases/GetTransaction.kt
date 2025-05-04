package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Transaction

class GetTransaction(
    private val transactionRepository: TransactionRepository,
) : Usecase<String, Transaction> {
    override suspend fun execute(input: String): Transaction {
        return transactionRepository.getById(input)!!
    }
}