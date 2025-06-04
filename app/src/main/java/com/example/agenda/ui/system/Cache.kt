package com.example.agenda.ui.system

import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.observer.Observer
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.MonthYearObj
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class Cache : Observer {
    val monthYearList = MutableStateFlow<MutableList<MonthYearObj>>(mutableListOf())
    val banks = MutableStateFlow(mutableListOf<Bank>())
    val transactions: MutableStateFlow<MutableList<Transaction>> =
        MutableStateFlow(mutableListOf())
    val goals = MutableStateFlow(mutableListOf<Goal>())
    fun getMonthYearList(): StateFlow<MutableList<MonthYearObj>> {
        runBlocking {
            if (monthYearList.value.isEmpty()) {
                resetCache()
            }
        }
        return monthYearList.asStateFlow()
    }

    fun getBanks(): StateFlow<MutableList<Bank>> {
        runBlocking {
            if (banks.value.isEmpty()) {
                App.UseCases.getAllBanks.execute(Unit)
            }
        }
        return banks.asStateFlow()
    }

    fun getTransactions(): StateFlow<MutableList<Transaction>> {
        runBlocking {
            if (transactions.value.isEmpty()) {
                App.UseCases.getAllTransactions.execute(Unit)
            }
        }

        return transactions.asStateFlow()
    }

    fun getGoals(): StateFlow<MutableList<Goal>> {
        runBlocking {
            if (goals.value.isEmpty()) {
                App.UseCases.getAllGoals.execute(Unit)
            }
        }
        return goals.asStateFlow()
    }

    private suspend fun resetCache() = coroutineScope {
        App.UseCases.getAllBanks.execute(Unit)
        //App.UseCases.getListOfTransactionsByMonthAndYear.execute(App.Time.today)
        App.UseCases.getAllTransactions.execute(Unit)
        App.UseCases.getAllGoals.execute(Unit)
    }

    override suspend fun update(event: ObserverEvents, value: Any) {
        when (event) {
            ObserverEvents.CREATE_TRANSACTION,
            ObserverEvents.DELETE_TRANSCATION,
            ObserverEvents.UPDATE_TRANSACTION,
            ObserverEvents.CREATE_EVENT,
            ObserverEvents.DELETE_EVENT,
            ObserverEvents.DELETE_BANK,
            ObserverEvents.UPDATE_BANK,
            ObserverEvents.CREATE_BANK,
            ObserverEvents.CREATE_GOAL,
                    ObserverEvents.DELETE_GOAL,
                -> {
                resetCache()
            }

            ObserverEvents.GET_WEEK_DATA_FUTURE -> {
                value as MonthYearObj; monthYearList.value.add(value)
            }

            ObserverEvents.GET_WEEK_DATA_PAST -> {
                value as MonthYearObj; monthYearList.value.add(0, value)
            }

            ObserverEvents.GET_WEEKS_DATA -> {
                value as MutableList<MonthYearObj>; monthYearList.value = value
            }

            ObserverEvents.GET_ALL_BANKS -> {
                value as MutableList<Bank>; banks.value = value
            }

            ObserverEvents.GET_LIST_OF_TRANSCATIONS_BY_MONTH_AND_YEAR -> {
               // value as MutableList<TransactionsMonthObject>; transactions.value = value
            }

            ObserverEvents.GET_ALL_GOALS -> {
                value as MutableList<Goal>; goals.value = value
            }
            ObserverEvents.GET_ALL_TRANSACTIONS -> {
                value as MutableList<Transaction>; transactions.value = value
            }

            ObserverEvents.BACKUP -> TODO()
        }
    }
}