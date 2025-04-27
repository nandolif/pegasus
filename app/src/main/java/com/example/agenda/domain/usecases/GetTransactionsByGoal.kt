package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.repositories.TransactionRepository

class GetTransactionsByGoal(
    private val transactionRepository: TransactionRepository,
    ): Usecase<GoalEntity, List<TransactionEntity>> {
    override suspend fun execute(input: GoalEntity): List<TransactionEntity> {
        return transactionRepository.getByGoal(input)
    }
}