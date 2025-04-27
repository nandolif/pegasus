package com.example.agenda.app.objects

import com.example.agenda.app.entities.EventEntity
import com.example.agenda.app.entities.TransactionEntity

interface DateObject {
    val date: DayMonthYearObject
    val transactions: List<TransactionEntity>
    val events: List<EventEntity>
}