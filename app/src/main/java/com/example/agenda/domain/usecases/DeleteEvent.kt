package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.app.repositories.EventRepository

class DeleteEvent(private val eventRepository: EventRepository) : Usecase<String, Unit>,Subject<DeleteEvent> {
    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: String) {
        val event = eventRepository.getById(input)

        if(event.recurrenceType == null && event.recurrenceId != null){
            val originalEvent = eventRepository.getById(event.recurrenceId!!)

            val recurrences = eventRepository.getByRecurrenceId(originalEvent.id!!)

            for (recurrence in recurrences) {
                eventRepository.delete(recurrence)
            }
            eventRepository.delete(originalEvent)
            return notifyAll(ObserverEvents.DELETE_EVENT, Unit)
        }
        if (event.recurrenceType != null) {
            val recurrences = eventRepository.getByRecurrenceId(event.id!!)

            for (recurrence in recurrences) {
                eventRepository.delete(recurrence)
            }
        }
        eventRepository.delete(event)
        notifyAll(ObserverEvents.DELETE_EVENT, Unit)
    }

}