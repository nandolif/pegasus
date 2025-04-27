package com.example.agenda.domain.repositories.box

import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.repositories.GoalRepository
import com.example.agenda.domain.entities.Goal
import io.objectbox.Box

class GoalBoxRepository(
    private val box: Box<Goal>,
) : GoalRepository {
    override suspend fun create(entity: GoalEntity) {
        box.put(entity as Goal)
    }

    override suspend fun update(entity: GoalEntity) {
        box.all.find { it.id == entity.id }?.let {
            box.put(
                it.copy(
                    title = entity.title,
                    amount = entity.amount,
                    achieved = entity.achieved,
                )
            )
        }
    }

    override suspend fun delete(entity: GoalEntity): Boolean {
        box.all.find { it.id == entity.id }?.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<GoalEntity> {
        return box.all
    }

    override suspend fun getById(id: String): GoalEntity {
        return box.all.find { it.id == id }!!
    }
}