package com.example.agenda.app.objects


interface MonthYearObject {
    val monthAndYear: DayMonthYearObject
    val weeks: List<WeekObject>
}