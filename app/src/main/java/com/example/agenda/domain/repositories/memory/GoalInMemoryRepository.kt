package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository

class GoalInMemoryRepository: GoalRepository {
    val goals = mutableListOf<GoalEntity>()
    override suspend fun getById(id: String): GoalEntity {
        return goals.first { it.id == id }
    }

    override suspend fun create(entity: GoalEntity) {
        goals.add(entity)
    }

    override suspend fun update(entity: GoalEntity) {
        goals[goals.indexOfFirst { it.id == entity.id }] = entity
    }

    override suspend fun delete(entity: GoalEntity): Boolean {
        return goals.remove(entity)
    }

    override suspend fun getAll(): List<GoalEntity> {
        return emptyList<GoalEntity>().plus(goals)
    }
}