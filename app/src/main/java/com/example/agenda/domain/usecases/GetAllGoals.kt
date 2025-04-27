package com.example.agenda.domain.usecases

import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository

class GetAllGoals(
    private val goalRepository: GoalRepository,
): Usecase<Unit, Unit>, Subject<GetAllGoals> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Unit) {
        val goals = goalRepository.getAll()
        val goalsList = mutableListOf<GoalEntity>()
        for (goal in goals){
            goalsList.add(App.UseCases.getGoal.execute(goal.id!!))
        }

        notifyAll(ObserverEvents.GET_ALL_GOALS, goalsList)
    }

}