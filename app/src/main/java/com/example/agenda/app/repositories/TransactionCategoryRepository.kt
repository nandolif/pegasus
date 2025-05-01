package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.app.common.repositories.CategoryRepository
import com.example.agenda.app.entities.TransactionCategoryEntity

interface TransactionCategoryRepository : CRUDRepository<TransactionCategoryEntity> {
    suspend fun getByName(name: String): TransactionCategoryEntity?
}