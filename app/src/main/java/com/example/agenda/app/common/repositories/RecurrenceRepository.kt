package com.example.agenda.app.common.repositories

interface RecurrenceRepository<T> {
    suspend fun getByRecurrence(): List<T>
    suspend fun getByRecurrenceId(id: String): List<T>
}