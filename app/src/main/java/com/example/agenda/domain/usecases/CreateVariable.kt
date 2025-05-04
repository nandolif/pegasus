package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.VariableRepository
import com.example.agenda.domain.entities.Variable

class CreateVariable(
    private val variableRepository: VariableRepository
): Usecase<Variable, Unit> {
    override suspend fun execute(input: Variable) {
         variableRepository.create(input)
    }
}