package com.example.agenda.domain.objects

data class MonthYearObj(
     val monthAndYear: DayMonthYearObj,
     var weeks: List<WeekObj>,
)