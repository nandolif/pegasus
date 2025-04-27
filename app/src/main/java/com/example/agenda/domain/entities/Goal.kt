package com.example.agenda.domain.entities

import com.example.agenda.app.entities.GoalEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Goal(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val achieved: Boolean,
    override val amount: Float,
    override val title: String,
    override val actualAmount: Float?,
) : GoalEntity {
    init {createMetadata()}
}