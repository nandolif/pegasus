package com.example.agenda.domain.repositories.memory


import com.example.agenda.app.entities.EventEntity
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.repositories.EventRepository

class EventInMemoryRepository : EventRepository {
    private val events = mutableListOf<EventEntity>()
    override suspend fun getByDate(date: DayMonthYearObject): List<EventEntity> {
        return events.filter { it.day == date.day && it.month == date.month && it.year == date.year }

    }

    override suspend fun getNByDate(date: DayMonthYearObject, quantity: Int): List<EventEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObject): List<EventEntity> {
        TODO("Not yet implemented")
    }


    override suspend fun create(entity: EventEntity) {
        events.add(entity)
    }

    override suspend fun update(entity: EventEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: EventEntity): Boolean {
        return events.remove(entity)
    }

    override suspend fun getAll(): List<EventEntity> {
        return events
    }

    override suspend fun getById(id: String): EventEntity {
        return events.first { it.id == id }
    }

    override suspend fun getByRecurrence(): List<EventEntity> {
        return events.filter { it.recurrenceType != null && it.recurrenceId == null }
    }

    override suspend fun getByRecurrenceId(id: String): List<EventEntity> {
        return events.filter { it.recurrenceId == id }
    }
}