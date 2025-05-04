package com.example.agenda.domain.repositories.memory


import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj

class EventInMemoryRepository : EventRepository {
    private val events = mutableListOf<Event>()
    override suspend fun getByDate(date: DayMonthYearObj): List<Event> {
        return events.filter { it.day == date.day && it.month == date.month && it.year == date.year }

    }

    override suspend fun getNByDate(date: DayMonthYearObj, quantity: Int): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObj): List<Event> {
        TODO("Not yet implemented")
    }


    override suspend fun create(entity: Event) {
        events.add(entity)
    }

    override suspend fun update(entity: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: Event): Boolean {
        return events.remove(entity)
    }

    override suspend fun getAll(): List<Event> {
        return events
    }

    override suspend fun getById(id: String): Event {
        return events.first { it.id == id }
    }

    override suspend fun getByRecurrence(): List<Event> {
        return events.filter { it.recurrenceType != null && it.recurrenceId == null }
    }

    override suspend fun getByRecurrenceId(id: String): List<Event> {
        return events.filter { it.recurrenceId == id }
    }
}