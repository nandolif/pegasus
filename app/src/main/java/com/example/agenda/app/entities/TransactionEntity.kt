package com.example.agenda.app.entities

import com.example.agenda.app.common.DateEntity
import com.example.agenda.app.common.Entity
import com.example.agenda.app.common.RecurrenceEntity

interface TransactionEntity: Entity, RecurrenceEntity, DateEntity{
    val amount: Float
    val description: String
    val bankId: String
    val goalId: String?
    val ghost: Boolean
    val canceled: Boolean
    val canceledDay: Int?
    val canceledMonth: Int?
    val canceledYear: Int?
}