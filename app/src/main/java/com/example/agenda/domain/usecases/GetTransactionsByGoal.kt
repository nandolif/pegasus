package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction

class GetTransactionsByGoal(
    private val transactionRepository: TransactionRepository,
    ): Usecase<Goal, List<Transaction>> {
    override suspend fun execute(input: Goal): List<Transaction> {
        return transactionRepository.getByGoal(input)
    }
}