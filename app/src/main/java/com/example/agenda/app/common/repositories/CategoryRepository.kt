package com.example.agenda.app.common.repositories

interface CategoryRepository {
    suspend fun deleteByCategory(id: String)
}