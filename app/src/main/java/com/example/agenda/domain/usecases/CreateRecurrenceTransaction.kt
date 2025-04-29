package com.example.agenda.domain.usecases

import com.example.agenda.app.common.Usecase
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.repositories.TransactionRepository
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DayMonthYearObj

class CreateRecurrenceTransaction(
    private val transactionRepository: TransactionRepository,
) : Usecase<DayMonthYearObj, Unit> {
    override suspend fun execute(input: DayMonthYearObj) {
        val recurrenceTransactions = transactionRepository.getByRecurrence()

        suspend fun createRecurrence(recurrenceTransaction: TransactionEntity) {
            var last = recurrenceTransaction

            val allRecurrenceChild =
                transactionRepository.getByRecurrenceId(recurrenceTransaction.id!!)
            if (allRecurrenceChild.isNotEmpty()) {
                last = allRecurrenceChild.last()
            }


            val lastDay = Date.getAllDaysByMonthAndYear(input.month, input.year).last()
            if (!Date.isInFuture(
                    date = DayMonthYearObj(
                        day = last.day,
                        month = last.month,
                        year = last.year
                    ), today = DayMonthYearObj(
                        day = lastDay,
                        month = input.month,
                        year = input.year
                    )
                )
            ) {
                val nextRecurrence = Date.getNextRecurrence(
                    DayMonthYearObj(
                        day = last.day,
                        month = last.month,
                        year = last.year
                    ),
                    recurrenceTransaction.recurrenceType!!,
                    recurrenceTransaction.nDays,
                    recurrenceTransaction.nWeeks,
                    recurrenceTransaction.nMonths,
                    recurrenceTransaction.nYears
                )

                val createTransaction = CreateTransaction(transactionRepository)
                        
                val newTransaction = Transaction(
                    day = nextRecurrence.day,
                    month = nextRecurrence.month,
                    year = nextRecurrence.year,
                    id = null,
                    amount = recurrenceTransaction.amount,
                    description = recurrenceTransaction.description,
                    created_at = null,
                    updated_at = null,
                    bankId = recurrenceTransaction.bankId,
                    recurrenceId = recurrenceTransaction.id,
                    ghost = false,
                    goalId = recurrenceTransaction.goalId,
                    canceled = false,
                    canceledDay = null,
                    canceledMonth = null,
                    canceledYear = null,
                    nDays = recurrenceTransaction.nDays,
                    nWeeks = recurrenceTransaction.nWeeks,
                    nMonths = recurrenceTransaction.nMonths,
                    nYears = recurrenceTransaction.nYears,
                    recurrenceType = recurrenceTransaction.recurrenceType,
                    categoryId = recurrenceTransaction.categoryId,
                )

                createTransaction.execute(newTransaction)

                if (!Date.isInFuture(
                        date = DayMonthYearObj(
                            day = nextRecurrence.day,
                            month = nextRecurrence.month,
                            year = nextRecurrence.year
                        ), today = DayMonthYearObj(
                            day = lastDay,
                            month = input.month,
                            year = input.year
                        )
                    )
                ) {
                    createRecurrence(recurrenceTransaction)
                }
            }


        }
        for (recurrenceTransaction in recurrenceTransactions) {
            createRecurrence(recurrenceTransaction)
        }
    }
}