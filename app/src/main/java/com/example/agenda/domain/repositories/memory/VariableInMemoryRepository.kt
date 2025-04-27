package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.entities.VariableEntity
import com.example.agenda.app.repositories.VariableRepository

class VariableInMemoryRepository: VariableRepository {
   val variables = mutableListOf<VariableEntity>()
    override suspend fun create(entity: VariableEntity) {
        variables.add(entity)
    }

    override suspend fun update(entity: VariableEntity){
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: VariableEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<VariableEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): VariableEntity {
        TODO("Not yet implemented")
    }
}