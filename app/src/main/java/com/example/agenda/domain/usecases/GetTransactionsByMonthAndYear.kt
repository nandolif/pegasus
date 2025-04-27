package com.example.agenda.domain.usecases

//import com.example.agenda.app.common.Usecase
//import com.example.agenda.app.objects.DayMonthYearObject
//import com.example.agenda.app.objects.TransactionsMonthObject
//import com.example.agenda.app.repositories.TransactionRepository
//import com.example.agenda.domain.objects.TransactionsMonthObj
//
//class GetTransactionsByMonthAndYear(
//    private val transactionRepository: TransactionRepository
//): Usecase<DayMonthYearObject, TransactionsMonthObject> {
//    override suspend fun execute(input: DayMonthYearObject): TransactionsMonthObject {
//        val allTransactions = transactionRepository.getByMonthAndYear(input)
//        return TransactionsMonthObj(input, allTransactions)
//    }
//}