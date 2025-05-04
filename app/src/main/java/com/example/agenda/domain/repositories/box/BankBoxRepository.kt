package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.domain.entities.Bank
import io.objectbox.Box

class BankBoxRepository(private val box: Box<Bank>) : BankRepository {
    override suspend fun create(entity: Bank) {
        box.put(entity as Bank)
    }

    override suspend fun update(entity: Bank) {
        box.all.first { it.id == entity.id }.let {
            box.put(
                it.copy(
                    name = entity.name,
                    balance = entity.balance,
                    updated_at = App.Time.now()
                )
            )
        }
    }

    override suspend fun delete(entity: Bank): Boolean {
        box.all.first { it.id == entity.id }.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<Bank> {
        return box.all
    }

    override suspend fun getById(id: String): Bank {
        return box.all.first { it.id == id }
    }
}