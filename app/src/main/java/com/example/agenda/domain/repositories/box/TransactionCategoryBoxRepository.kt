package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.TransactionCategoryRepository
import com.example.agenda.domain.entities.TransactionCategory
import io.objectbox.Box

class TransactionCategoryBoxRepository(
    val box: Box<TransactionCategory>,
) : TransactionCategoryRepository {

    override suspend fun create(entity: TransactionCategory) {
        box.put(entity)
    }

    override suspend fun update(entity: TransactionCategory) {
        box.all.first { it.id == entity.id }.let {
            box.put(it.copy(
                name = entity.name,
                updated_at = App.Time.now(),
                textColor = entity.textColor,
                backgroundColor = entity.backgroundColor
            ))
        }
    }

    override suspend fun delete(entity: TransactionCategory): Boolean {
        box.all.first { it.id == entity.id }.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<TransactionCategory> {
        return box.all
    }

    override suspend fun getById(id: String): TransactionCategory? {
        if (box.all.isEmpty()) return  null
        return box.all.firstOrNull { it.id == id }
    }
}