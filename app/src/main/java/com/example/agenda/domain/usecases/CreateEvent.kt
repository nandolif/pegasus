package com.example.agenda.domain.usecases

import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.app.common.Usecase
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.app.common.observer.Subject
import com.example.agenda.domain.entities.Event


class CreateEvent(
    private val repository: EventRepository
): Usecase<Event, Unit>,Subject<CreateEvent> {

    override val observers: MutableList<Observer> = mutableListOf()
    override suspend fun execute(input: Event) {
        repository.create(input)
        notifyAll(ObserverEvents.CREATE_EVENT,input)
    }
}