package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.app.common.repositories.DateRepository
import com.example.agenda.app.common.repositories.RecurrenceRepository
import com.example.agenda.app.entities.EventEntity

interface EventRepository: DateRepository<EventEntity>, CRUDRepository<EventEntity>,
    RecurrenceRepository<EventEntity> {
}