package com.example.agenda.domain.objects

import com.example.agenda.app.objects.DayMonthYearObject

data class DayMonthYearObj(
    override val day: Int,
    override val month: Int,
    override val year: Int
) : DayMonthYearObject