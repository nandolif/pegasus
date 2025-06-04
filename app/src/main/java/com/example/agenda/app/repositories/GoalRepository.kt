package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.GoalDefault
import kotlinx.coroutines.runBlocking

interface GoalRepository: CRUDRepository<Goal> {
    fun setup() {
        _setup(GoalDefault.ID, GoalDefault.AMOUNT)
    }
    private fun _setup(id: String, amount: Double) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    Goal(
                        id = id,
                        title = id,
                        created_at = null,
                        updated_at = null,
                        achieved = false,
                        amount = amount,
                        actualAmount = 0.0
                    )
                )
            }
        }
    }
}