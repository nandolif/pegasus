package com.example.agenda.domain.objects

import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory

data class TransactionWithData(
    val transaction: Transaction,
    val bank: Bank,
    val category: TransactionCategory
)