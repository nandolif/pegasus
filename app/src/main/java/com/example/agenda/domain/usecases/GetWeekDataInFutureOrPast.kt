package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.objects.DayMonthYearObj

@Suppress("IMPLICIT_CAST_TO_ANY")
class GetWeekDataInFutureOrPast(
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
) : Usecase<List<Any>, Unit>, Subject<GetWeekDataInFutureOrPast> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: List<Any>) {
        val date = input[0] as DayMonthYearObj
        val isInFuture = input[1] as Boolean
        val weekData = GetWeekData(transactionRepository, eventRepository).execute(date)
        if (isInFuture) {
            notifyAll(ObserverEvents.GET_WEEK_DATA_FUTURE, weekData)
        } else {
            notifyAll(ObserverEvents.GET_WEEK_DATA_PAST, weekData)
        }
    }
}