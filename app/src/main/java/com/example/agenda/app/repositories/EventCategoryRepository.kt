package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.ui.screens.EventCategories
import kotlinx.coroutines.runBlocking

interface EventCategoryRepository: CRUDRepository<EventCategory> {
    fun setup() {
        _setup(
            EventCategories.Default.Others.NAME_AND_ID,
            backgroundColor = EventCategories.Default.Others.BACKGROUND_COLOR,
            textColor = EventCategories.Default.Others.TEXT_COLOR
        )
    }

    private fun _setup(id: String, backgroundColor: String, textColor: String) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    EventCategory(
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