package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id



object GoalDefault {
    const val ID = "Guarde 1000 Reais"
    const val AMOUNT = 1000.0
}

@E
data class Goal(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    val achieved: Boolean,
    val amount: Double,
    val title: String,
    val actualAmount: Double?,
) : Entity {init {createMetadata()}}