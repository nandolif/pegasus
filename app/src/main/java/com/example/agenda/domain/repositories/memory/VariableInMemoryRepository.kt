package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.repositories.VariableRepository
import com.example.agenda.domain.entities.Variable

class VariableInMemoryRepository: VariableRepository {
   val variables = mutableListOf<Variable>()
    override suspend fun create(entity: Variable) {
        variables.add(entity)
    }

    override suspend fun update(entity: Variable){
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: Variable): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Variable> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): Variable {
        TODO("Not yet implemented")
    }
}