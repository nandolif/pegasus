package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.repositories.BankRepository

class BankInMemoryRepository :
    BankRepository {
    val banks = mutableListOf<BankEntity>()

    override suspend fun getById(id: String): BankEntity {
        return banks.find { it.id == id }!!
    }

    override suspend fun create(entity: BankEntity) {
        banks.add(entity)
    }

    override suspend fun update(entity: BankEntity){
        val index = banks.indexOfFirst { it.id == entity.id }
        banks[index] = entity
    }

    override suspend fun delete(entity: BankEntity): Boolean {
        return banks.remove(entity)
    }

    override suspend fun getAll(): List<BankEntity> {
        return emptyList<BankEntity>().plus(banks)
    }
}