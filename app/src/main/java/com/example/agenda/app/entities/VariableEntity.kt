package com.example.agenda.app.entities

import com.example.agenda.app.common.Entity

interface VariableEntity: Entity {
    val name: String
    val value: String
}