package com.example.agenda.app.entities

import com.example.agenda.app.common.DateEntity
import com.example.agenda.app.common.Entity
import com.example.agenda.app.common.EventType
import com.example.agenda.app.common.RecurrenceEntity


interface EventEntity: Entity, RecurrenceEntity, DateEntity{
    val description: String
    val eventType: EventType
    val categoryId: String
}