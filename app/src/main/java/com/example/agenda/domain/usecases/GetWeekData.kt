package com.example.agenda.domain.usecases

import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DateObj
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import com.example.agenda.domain.objects.WeekObj

class GetWeekData(
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
) : Usecase<DayMonthYearObj, MonthYearObj> {
    override suspend fun execute(input: DayMonthYearObj): MonthYearObj {


        CreateRecurrenceEvent(eventRepository).execute(input)

        val weeks = Date.getWeeks(input, 6).toMutableList()

        val newWeeks = mutableListOf<WeekObj>()
        for (week in weeks) {
            val d = mutableListOf<DateObj>()
            for (day in week.days) {
                val transactions = transactionRepository.getByDate(day.date)
                val events = eventRepository.getByDate(day.date)
                d.add(
                    DateObj(
                        date = day.date,
                        transactions = transactions,
                        events = events
                    )
                )
            }
            val w = WeekObj(days = d)
            newWeeks.add(w)
        }
        return MonthYearObj(
            monthAndYear = DayMonthYearObj(day = 1, month = input.month, year = input.year),
            weeks = newWeeks
        )
    }
}

