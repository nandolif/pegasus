package com.example.agenda.domain.entities

import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.domain.repositories.box.RecurrenceConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Transaction(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override val day: Int,
    override val month: Int,
    override val year: Int,
    override val amount: Float,
    override val description: String,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val bankId: String,
    override val recurrenceId: String?,
    override val ghost: Boolean,
    override val goalId: String?,
    override val canceled: Boolean,
    override val canceledDay: Int?,
    override val canceledMonth: Int?,
    override val canceledYear: Int?,
    override val nDays: Int?,
    override val nWeeks: Int?,
    override val nMonths: Int?,
    override val nYears: Int?,
    @Convert(converter = RecurrenceConverter::class, dbType = String::class)
    override val recurrenceType: RECURRENCE?,
) : TransactionEntity {
    init {createMetadata()}
}

