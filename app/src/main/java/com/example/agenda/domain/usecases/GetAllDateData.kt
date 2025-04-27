package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.objects.DateObj
import com.example.agenda.domain.objects.DayMonthYearObj


class GetAllDateData(
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
) : Usecase<DayMonthYearObj, Unit> {
    override suspend fun execute(input: DayMonthYearObj) {
        val transactions = transactionRepository.getByDate(input)
        val events = eventRepository.getByDate(input)

        DateObj(
            date = input,
            transactions = transactions,
            events = events,
        )
    }
}