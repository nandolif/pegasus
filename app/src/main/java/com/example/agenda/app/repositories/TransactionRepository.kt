package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.app.common.repositories.CategoryRepository
import com.example.agenda.app.common.repositories.DateRepository
import com.example.agenda.app.common.repositories.RecurrenceRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction

interface TransactionRepository : DateRepository<Transaction>,
    CRUDRepository<Transaction>, RecurrenceRepository<Transaction>, CategoryRepository {
   suspend fun getByBank(bank: Bank): List<Transaction>
   suspend fun getByGoal(goal: Goal): List<Transaction>
}