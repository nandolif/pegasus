package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Person
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.screens.TransactionCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class TransactionsVM : ViewModel() {
    val transactions = App.UI.cache.getTransactions()
    val banks = MutableStateFlow(mutableListOf<Bank>())
    val goals = App.UI.cache.getGoals()
    val persons = MutableStateFlow(mutableListOf<Person>())
    val expenseCategories = MutableStateFlow(mutableListOf<TransactionCategory>())
    val incomeCategories = MutableStateFlow(mutableListOf<TransactionCategory>())
    val transferCategories = MutableStateFlow(mutableListOf<TransactionCategory>())

    fun getTransaction(id: String?): Transaction? {
        var actualTransaction: Transaction? = null
        if(id != null) {
            runBlocking {
                actualTransaction = App.Repositories.transactionRepository.getById(id)
            }
        }
        return actualTransaction
    }

    init {
        runBlocking {
            expenseCategories.value =
                App.Repositories.transactionCategoryRepository.getExpenseCategories()
                    .filter { it.id != TransactionCategories.Default.Goal.NAME_AND_ID }
                    .toMutableList()
            incomeCategories.value =
                App.Repositories.transactionCategoryRepository.getIncomeCategories().toMutableList()
            transferCategories.value =
                App.Repositories.transactionCategoryRepository.getTransferCategories()
                    .toMutableList()
            banks.value = App.UseCases.getAllBanks.execute(Unit).toMutableList()
            persons.value = App.Repositories.personRepository.getAll().toMutableList()
        }
    }
}