package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.domain.entities.Goal

class GoalInMemoryRepository: GoalRepository {
    val goals = mutableListOf<Goal>()
    override suspend fun getById(id: String): Goal {
        return goals.first { it.id == id }
    }

    override suspend fun create(entity: Goal) {
        goals.add(entity)
    }

    override suspend fun update(entity: Goal) {
        goals[goals.indexOfFirst { it.id == entity.id }] = entity
    }

    override suspend fun delete(entity: Goal): Boolean {
        return goals.remove(entity)
    }

    override suspend fun getAll(): List<Goal> {
        return emptyList<Goal>().plus(goals)
    }
}