package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.repositories.TransactionCategoryRepository
import com.example.agenda.domain.entities.TransactionCategory
import io.objectbox.Box

class TransactionCategoryBoxRepository(
    val box: Box<TransactionCategory>,
) : TransactionCategoryRepository {
    override suspend fun getByName(name: String): TransactionCategoryEntity? {
        return box.all.firstOrNull { it.name == name}
    }

    override suspend fun create(entity: TransactionCategoryEntity) {
        box.put(entity as TransactionCategory)
    }

    override suspend fun update(entity: TransactionCategoryEntity) {
        box.all.first { it.id == entity.id }.let {
            box.put(it.copy(
                name = entity.name,
                updated_at = App.Time.now()
            ))
        }
    }

    override suspend fun delete(entity: TransactionCategoryEntity): Boolean {
        box.all.first { it.id == entity.id }.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<TransactionCategoryEntity> {
        return box.all
    }

    override suspend fun getById(id: String): TransactionCategoryEntity {
        return box.all.first { it.id == id }
    }
}