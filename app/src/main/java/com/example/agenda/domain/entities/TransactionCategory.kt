package com.example.agenda.domain.entities

import com.example.agenda.app.common.ColorEntity
import com.example.agenda.app.common.Entity
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id

@E
data class TransactionCategory(
    @Id
    var _id: Long = 0,
    override var id: String?,
    val name: String,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val textColor: String,
    override val backgroundColor: String,
) : Entity, ColorEntity {init {createMetadata()}}