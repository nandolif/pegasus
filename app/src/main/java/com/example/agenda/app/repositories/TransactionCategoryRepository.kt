package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.app.common.repositories.CategoryRepository
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.screens.TransactionCategories
import kotlinx.coroutines.runBlocking

interface TransactionCategoryRepository : CRUDRepository<TransactionCategoryEntity> {
    fun setup() {
        _setup(TransactionCategories.GOAL_CATEGORY_NAME_AND_ID)
        _setup(TransactionCategories.OTHERS_CATEGORY_NAME_AND_ID)
    }

    private fun _setup(id: String) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    TransactionCategory(
                        id = id, name = id,
                        created_at = null,
                        updated_at = null
                    )
                )
            }
        }
    }
}