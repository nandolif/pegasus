package com.example.agenda.app.common


interface RecurrenceEntity {
    val recurrenceType: RECURRENCE?
    val nDays: Int?
    val nWeeks: Int?
    val nMonths: Int?
    val nYears: Int?
    val recurrenceId: String?
}