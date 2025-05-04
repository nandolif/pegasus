package com.example.agenda.domain.entities


import com.example.agenda.app.common.Entity
import com.example.agenda.app.common.EventType
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.common.RecurrenceEntity
import com.example.agenda.domain.repositories.box.EventTypeConverter
import com.example.agenda.domain.repositories.box.RecurrenceConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id

@E
data class Event(
    @Id
    var _id: Long = 0,
    val day: Int,
    val month: Int,
    val year: Int,
    val description: String,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    @Convert(converter = RecurrenceConverter::class, dbType = String::class)
    override val recurrenceType: RECURRENCE?,
    override val nDays: Int?,
    override val nWeeks: Int?,
    override val nMonths: Int?,
    override val nYears: Int?,
    override val recurrenceId: String?,
    @Convert(converter = EventTypeConverter::class, dbType = String::class)
    val eventType: EventType,
    val categoryId: String,
) : Entity, RecurrenceEntity {init {createMetadata()}}