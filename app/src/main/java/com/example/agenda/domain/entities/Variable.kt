package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity


class Variable(
    override var id: String?,
    val name: String,
    val value: String,
    override var created_at: Long?,
    override var updated_at: Long?,
) : Entity {init {createMetadata() }}