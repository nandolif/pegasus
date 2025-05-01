package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.objects.DayMonthYearObj

class GetBank(
    private val bankRepository: BankRepository,
    private val transactionRepository: TransactionRepository,
    private val today: DayMonthYearObject = Date.getToday(),
) : Usecase<String, BankEntity> {
    override suspend fun execute(input: String): BankEntity {
        val bank = bankRepository.getById(input)
        val transactions = transactionRepository.getByBank(bank!!)

        var amount = bank!!.balance
        for (transaction in transactions) {
            if (transaction.ghost) continue
            if (Date.isInFuture(
                    DayMonthYearObj(
                        day = transaction.day,
                        month = transaction.month,
                        year = transaction.year
                    ), today
                )
            ) continue
            if (transaction.goalId == null) {
                amount += transaction.amount
            } else {
                amount -= transaction.amount
            }
        }
        return Bank(
            id = bank.id,
            created_at = bank.created_at,
            updated_at = bank.updated_at,
            balance = amount,
            name = bank.name,
        )
    }
}