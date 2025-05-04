package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.Variable

interface VariableRepository: CRUDRepository<Variable> {
}