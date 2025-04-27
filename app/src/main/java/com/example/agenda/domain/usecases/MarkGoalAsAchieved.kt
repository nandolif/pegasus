package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.domain.entities.Goal

class MarkGoalAsAchieved(
    private val goalRepository: GoalRepository,
) : Usecase<GoalEntity, Unit> {
    override suspend fun execute(input: GoalEntity) {
        val goal = Goal(
            actualAmount = input.actualAmount,
            amount = input.amount,
            title = input.title,
            achieved = true,
            id = input.id,
            created_at = input.created_at,
            updated_at = input.updated_at,
        )

        goalRepository.update(goal)
    }
}
