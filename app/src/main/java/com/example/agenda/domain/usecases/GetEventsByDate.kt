package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj

class GetEventsByDate(
    private val eventRepository: EventRepository
): Usecase<DayMonthYearObj, List<Event>> {
    override suspend fun execute(input: DayMonthYearObj): List<Event> {
        return eventRepository.getByDate(input)
    }
}