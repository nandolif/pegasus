package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.screens.TransactionCategories
import kotlinx.coroutines.runBlocking

interface TransactionCategoryRepository : CRUDRepository<TransactionCategory> {
    fun setup() {
        _setup(
            TransactionCategories.Default.Goal.NAME_AND_ID,
            backgroundColor = TransactionCategories.Default.Goal.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.Goal.TEXT_COLOR
        )
        _setup(
            TransactionCategories.Default.Others.NAME_AND_ID,
            backgroundColor = TransactionCategories.Default.Others.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.Others.TEXT_COLOR
        )
    }

    private fun _setup(id: String, backgroundColor: String, textColor: String) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    TransactionCategory(
                        id = id, name = id,
                        created_at = null,
                        updated_at = null,
                        textColor = textColor,
                        backgroundColor = backgroundColor
                    )
                )
            }
        }
    }
}