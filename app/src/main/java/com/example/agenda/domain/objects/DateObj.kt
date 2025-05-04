package com.example.agenda.domain.objects

import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.entities.Transaction

data class DateObj(
    val date: DayMonthYearObj,
    val transactions: List<Transaction>,
    val events: List<Event>,
)