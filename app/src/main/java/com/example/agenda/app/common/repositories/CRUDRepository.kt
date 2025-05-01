package com.example.agenda.app.common.repositories

interface CRUDRepository<T> {
    suspend fun create(entity: T)
    suspend fun update(entity: T)
    suspend fun delete(entity: T): Boolean
    suspend fun getAll(): List<T>
    suspend fun getById(id: String): T?
}