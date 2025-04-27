package com.example.agenda.domain.objects

import com.example.agenda.app.entities.EventEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.objects.DateObject
import com.example.agenda.app.objects.DayMonthYearObject

data class DateObj(
    override val date: DayMonthYearObject,
    override val transactions: List<TransactionEntity>,
    override val events: List<EventEntity>,
) : DateObject