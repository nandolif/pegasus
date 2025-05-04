package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.EventCategoryRepository
import com.example.agenda.domain.entities.EventCategory
import io.objectbox.Box

class EventCategoryBoxRepository    (val box: Box<EventCategory>): EventCategoryRepository {
    override suspend fun create(entity: EventCategory) {
        box.put(entity)
    }

    override suspend fun update(entity: EventCategory) {
        box.all.firstOrNull { it.id == entity.id }?.let {
            box.put(it.copy(
                name = entity.name,
                textColor = entity.textColor,
                backgroundColor = entity.backgroundColor,
                updated_at = App.Time.now()
            ))
        }
    }

    override suspend fun delete(entity: EventCategory): Boolean {
        box.all.firstOrNull { it.id == entity.id }?.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<EventCategory> {
        return box.all
    }

    override suspend fun getById(id: String): EventCategory? {
        if(box.all.isEmpty()) return null
        return box.all.firstOrNull { it.id == id }
    }
}