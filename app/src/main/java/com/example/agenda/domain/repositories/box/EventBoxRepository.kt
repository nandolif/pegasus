package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj
import io.objectbox.Box

class EventBoxRepository(
    private val box: Box<Event>
): EventRepository {
    override suspend fun getByDate(date: DayMonthYearObj): List<Event> {
        return box.all.filter { it.day == date.day && it.month == date.month && it.year == date.year }
    }

    override suspend fun getNByDate(date: DayMonthYearObj, quantity: Int): List<Event> {
        return box.all.filter { it.day == date.day && it.month == date.month && it.year == date.year }.take(quantity)
    }

    override suspend fun getByMonthAndYear(date: DayMonthYearObj): List<Event> {
        return box.all.filter { it.month == date.month && it.year == date.year }
    }

    override suspend fun create(entity: Event) {
        box.put(entity)
    }

    override suspend fun update(entity: Event) {
        box.all.find { it.id == entity.id }?.let {
            box.put(it.copy(
                description = entity.description,
                day = entity.day,
                month = entity.month,
                year = entity.year,
                updated_at = App.Time.now(),
                recurrenceId = entity.recurrenceId,
                eventType = entity.eventType,
                recurrenceType = entity.recurrenceType,
                nDays = entity.nDays,
                nMonths = entity.nMonths,
                nWeeks = entity.nWeeks,
                nYears = entity.nYears,
            ))
        }
    }

    override suspend fun delete(entity: Event): Boolean {
        box.all.find { it.id == entity.id }?.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<Event> {
        return box.all
    }

    override suspend fun getById(id: String): Event {
        return box.all.find { it.id == id }!!
    }

    override suspend fun getByRecurrence(): List<Event> {
        return box.all.filter { it.recurrenceType != null }
    }

    override suspend fun getByRecurrenceId(id: String): List<Event> {
        return box.all.filter { it.recurrenceId == id }
    }
}