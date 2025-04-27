package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.app.common.repositories.DateRepository
import com.example.agenda.app.common.repositories.RecurrenceRepository
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionEntity

interface TransactionRepository : DateRepository<TransactionEntity>,
    CRUDRepository<TransactionEntity>, RecurrenceRepository<TransactionEntity> {
   suspend fun getByBank(bank: BankEntity): List<TransactionEntity>
   suspend fun getByGoal(goal: GoalEntity): List<TransactionEntity>
}