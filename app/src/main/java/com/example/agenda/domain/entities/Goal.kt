package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id

@E
data class Goal(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    val achieved: Boolean,
    val amount: Float,
    val title: String,
    val actualAmount: Float?,
) : Entity {init {createMetadata()}}