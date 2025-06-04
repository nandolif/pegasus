package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.TransactionCategoryRepository
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.screens.Transactions
import io.objectbox.Box

class TransactionCategoryBoxRepository(
    val box: Box<TransactionCategory>,
) : TransactionCategoryRepository {
    override suspend fun getExpenseCategories(): List<TransactionCategory> {
        return box.all.filter { it.type == Transactions.Type.EXPENSE }
    }

    override suspend fun getIncomeCategories(): List<TransactionCategory> {
        return box.all.filter { it.type == Transactions.Type.INCOME }
    }

    override suspend fun getTransferCategories(): List<TransactionCategory> {
        return box.all.filter { it.type == Transactions.Type.TRANSFER }
    }

    override suspend fun create(entity: TransactionCategory) {
        box.put(entity)
    }

    override suspend fun update(entity: TransactionCategory) {
        box.all.first { it.id == entity.id }.let {
            box.put(it.copy(
                name = entity.name,
                updated_at = App.Time.now(),
                textColor = entity.textColor,
                backgroundColor = entity.backgroundColor,
                type = entity.type
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