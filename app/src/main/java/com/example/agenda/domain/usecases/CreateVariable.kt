package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.VariableEntity
import com.example.agenda.app.repositories.VariableRepository

class CreateVariable(
    private val variableRepository: VariableRepository
): Usecase<VariableEntity, Unit> {
    override suspend fun execute(input: VariableEntity) {
         variableRepository.create(input)
    }
}