package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.domain.entities.Goal

class CreateGoal(
    private val goalRepository: GoalRepository,
) : Usecase<GoalEntity, Unit>, Subject<CreateGoal> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: GoalEntity) {
        val goal = Goal(
            amount = input.amount,
            title = input.title,
            id = null,
            created_at = null,
            updated_at = null,
            achieved = false,
            actualAmount = null,
        )
        notifyAll(ObserverEvents.CREATE_GOAL,goalRepository.create(goal))
    }
}