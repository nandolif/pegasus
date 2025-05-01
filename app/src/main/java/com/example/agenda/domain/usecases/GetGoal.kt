package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Goal

class GetGoal(
    private val goalRepository: GoalRepository,
    private val transactionRepository: TransactionRepository,
) : Usecase<String, GoalEntity> {
    override suspend fun execute(input: String): GoalEntity {
        val goal = goalRepository.getById(input)
        val allTransactions = transactionRepository.getByGoal(goal!!)
        var actualAmount = 0f

        allTransactions.forEach {
            if (!it.ghost) {
                actualAmount += it.amount
            }
        }

         return Goal(
            actualAmount = actualAmount,
            amount = goal.amount,
            title = goal.title,
            achieved = goal.achieved,
            id = goal.id,
            created_at = goal.created_at,
            updated_at = goal.updated_at,
        )
    }
}