package com.example.agenda.domain.repositories.memory

import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.domain.entities.Bank

class BankInMemoryRepository :
    BankRepository {
    val banks = mutableListOf<Bank>()

    override suspend fun getById(id: String): Bank {
        return banks.find { it.id == id }!!
    }

    override fun onlyWithCredit(): List<Bank> {
        return emptyList<Bank>().plus(banks).filter { it.creditLimit != null }
    }

    override suspend fun create(entity: Bank) {
        banks.add(entity)
    }

    override suspend fun update(entity: Bank){
        val index = banks.indexOfFirst { it.id == entity.id }
        banks[index] = entity
    }

    override suspend fun delete(entity: Bank): Boolean {
        return banks.remove(entity)
    }

    override suspend fun getAll(): List<Bank> {
        return emptyList<Bank>().plus(banks)
    }
}