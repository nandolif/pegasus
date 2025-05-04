package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.helps.Date
import com.example.agenda.app.repositories.EventRepository
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj

class CreateRecurrenceEvent(
    private val eventRepository: EventRepository,
) : Usecase<DayMonthYearObj, Unit> {
    override suspend fun execute(input: DayMonthYearObj) {
        val events = eventRepository.getByRecurrence()
        suspend fun createRecurrence(event: Event) {
            val children = eventRepository.getByRecurrenceId(event.id!!)
            val lastChild = if(children.isNotEmpty()) children.last() else event
            val lastDayDate = Date.getWeeks(input,6).last().days.last().date
            if (Date.isInFuture(
                    DayMonthYearObj(lastChild.day, lastChild.month, lastChild.year),
                    lastDayDate
                )
            ) {
                return
            }
            val nextRecurrence = Date.getNextRecurrence(
                date = DayMonthYearObj(lastChild.day, lastChild.month, lastChild.year),
                type = event.recurrenceType!!,
                nDays = event.nDays,
                nWeeks = event.nWeeks,
                nMonths = event.nMonths,
                nYears = event.nYears
            )

            eventRepository.create(
                Event(
                    day = nextRecurrence.day,
                    month = nextRecurrence.month,
                    year = nextRecurrence.year,
                    description = event.description,
                    id = null,
                    created_at = null,
                    updated_at = null,
                    recurrenceType = null,
                    nDays = null,
                    nWeeks = null,
                    nMonths = null,
                    nYears = null,
                    recurrenceId = event.id,
                    eventType = event.eventType,
                    categoryId = event.categoryId
                )
            )


            if (!Date.isInFuture(nextRecurrence, lastDayDate)) {
                createRecurrence(event)
            }
        }
        for (event in events) {
            createRecurrence(event)
        }
    }

}
