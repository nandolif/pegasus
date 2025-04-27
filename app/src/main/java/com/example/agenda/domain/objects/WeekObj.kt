package com.example.agenda.domain.objects

import com.example.agenda.app.objects.DateObject
import com.example.agenda.app.objects.WeekObject

data class WeekObj(
    override val days: List<DateObject>
): WeekObject