package com.example.agenda.domain.objects

import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.objects.MonthYearObject
import com.example.agenda.app.objects.WeekObject

data class MonthYearObj(
    override val monthAndYear: DayMonthYearObject,
    override var weeks: List<WeekObject>,
): MonthYearObject