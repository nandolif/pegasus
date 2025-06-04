package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.helps.Date
import com.example.agenda.app.repositories.BankRepository
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.objects.DayMonthYearObj

class GetBank(
    private val bankRepository: BankRepository,
    private val transactionRepository: TransactionRepository,
    private val today: DayMonthYearObj = Date.getToday(),
) : Usecase<String, Bank> {
    override suspend fun execute(input: String): Bank {
        val bank = bankRepository.getById(input)
        val transactions = transactionRepository.getByBank(bank!!)

        var amount = bank!!.balance
        var creditSpent = bank.creditSpent ?: Money.ZERO

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
                if (transaction.isCredit) {
                    creditSpent += transaction.amount
                } else {

                    amount += transaction.amount
                }
            } else {
                if (transaction.isCredit) {
                    creditSpent -= transaction.amount
                } else {
                    amount -= transaction.amount
                }
            }
        }
        return bank.copy(balance = amount, creditSpent = if(creditSpent != Money.ZERO || bank.creditLimit != null) creditSpent else null)
    }
}