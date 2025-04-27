package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.EventEntity
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.repositories.EventRepository

class GetEventsByDate(
    private val eventRepository: EventRepository
): Usecase<DayMonthYearObject, List<EventEntity>> {
    override suspend fun execute(input: DayMonthYearObject): List<EventEntity> {
        return eventRepository.getByDate(input)
    }
}