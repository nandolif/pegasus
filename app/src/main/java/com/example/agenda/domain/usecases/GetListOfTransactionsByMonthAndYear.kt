package com.example.agenda.domain.usecases
//
//import com.example.agenda.app.App
//import com.example.agenda.app.common.ObserverEvents
//import com.example.agenda.app.common.Usecase
//import com.example.agenda.app.common.observer.Observer
//import com.example.agenda.app.common.observer.Subject
//import com.example.agenda.app.helps.Date
//import com.example.agenda.app.objects.DayMonthYearObject
//import com.example.agenda.app.objects.TransactionsMonthObject
//import com.example.agenda.app.repositories.TransactionRepository
//import com.example.agenda.domain.objects.DayMonthYearObj
//
//class GetListOfTransactionsByMonthAndYear(
//    private val transactionRepository: TransactionRepository
//): Usecase<DayMonthYearObject, Unit>, Subject<GetListOfTransactionsByMonthAndYear> {
//    override val observers: MutableList<Observer> = mutableListOf()
//
//    override suspend fun execute(input: DayMonthYearObject){
//        val transactions = mutableListOf<TransactionsMonthObject>()
//        var date = Date.getDate(DayMonthYearObj(input.day,input.month -2 , input.year))
//
//        for (i in 0..<App.UI.pageRange.size) {
//            date = Date.getDate(DayMonthYearObj(date.day,date.month + 1 , date.year))
//            val tm = App.UseCases.getTransactionsByMonthAndYear.execute(date)
//            transactions.add(tm)
//        }
//
//        notifyAll(ObserverEvents.GET_LIST_OF_TRANSCATIONS_BY_MONTH_AND_YEAR, transactions)
//    }
//
//}