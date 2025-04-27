package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.app.repositories.TransactionRepository

class DeleteGoal(
    private val goalRepository: GoalRepository,
    private val transactionRepository: TransactionRepository,
): Usecase<GoalEntity, Unit>, Subject<DeleteGoal> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: GoalEntity) {
        val transactions = transactionRepository.getByGoal(input)
        for (transaction in transactions) {
            transactionRepository.delete(transaction)
        }
        goalRepository.delete(input)
        notifyAll(ObserverEvents.DELETE_GOAL, Unit)
    }

}