package com.example.agenda.domain.entities

import com.example.agenda.app.entities.EventCategoryEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class EventCategory(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val name: String,
    override val textColor: String,
    override val backgroundColor: String
): EventCategoryEntity{init{createMetadata()}}