package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.repositories.TransactionRepository

class GetTransaction(
    private val transactionRepository: TransactionRepository,
) : Usecase<String, TransactionEntity> {
    override suspend fun execute(input: String): TransactionEntity {
        return transactionRepository.getById(input)!!
    }
}