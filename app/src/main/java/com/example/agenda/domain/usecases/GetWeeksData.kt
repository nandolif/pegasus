package com.example.agenda.domain.usecases

import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.helps.Date
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj

class GetWeeksData(
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
) : Usecase<DayMonthYearObj, Unit>, Subject<GetWeeksData> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: DayMonthYearObj) {
        var date = Date.getDate(DayMonthYearObj(1, input.month - 2, input.year))
        val monthAndYearList = mutableListOf<MonthYearObj>()

        for (i in 0..<App.UI.pageRange.size) {
            date = Date.getDate(DayMonthYearObj(1, date.month + 1, date.year))
            val monthAndYear = GetWeekData(transactionRepository, eventRepository).execute(date)
            monthAndYearList.add(monthAndYear)
        }
        notifyAll(ObserverEvents.GET_WEEKS_DATA, monthAndYearList)
    }
}

