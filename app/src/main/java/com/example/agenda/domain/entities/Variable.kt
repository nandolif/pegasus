package com.example.agenda.domain.entities

import com.example.agenda.app.entities.VariableEntity

class Variable(
    override var id: String?,
    override val name: String,
    override val value: String,
    override var created_at: Long?,
    override var updated_at: Long?
): VariableEntity {
    init {createMetadata()}
}