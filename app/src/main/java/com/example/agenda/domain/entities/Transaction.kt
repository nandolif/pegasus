package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.common.RecurrenceEntity
import com.example.agenda.domain.repositories.box.RecurrenceConverter
import com.example.agenda.ui.screens.TransactionCategories
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id

@E
data class Transaction(
    @Id
    var _id: Long = 0,
    override var id: String?,
    val day: Int,
    val month: Int,
    val year: Int,
    val amount: Double,
    val description: String,
    override var created_at: Long?,
    override var updated_at: Long?,
    val bankId: String,
    override val recurrenceId: String?,
    val ghost: Boolean,
    val goalId: String?,
    val canceled: Boolean,
    val canceledDay: Int?,
    val canceledMonth: Int?,
    val canceledYear: Int?,
    override val nDays: Int?,
    override val nWeeks: Int?,
    override val nMonths: Int?,
    override val nYears: Int?,
    @Convert(converter = RecurrenceConverter::class, dbType = String::class)
    override val recurrenceType: RECURRENCE?,
    var categoryId: String,
    val personId: String?
) : Entity, RecurrenceEntity {init { createMetadata();if (goalId != null) categoryId = TransactionCategories.Default.Goal.NAME_AND_ID}}


