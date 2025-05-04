package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.TransactionCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class TransactionsVM : ViewModel() {
    val transactions = App.UI.cache.getTransactions()
    val banks = App.UI.cache.getBanks()
    val goals = App.UI.cache.getGoals()
    val categories = MutableStateFlow(mutableListOf<TransactionCategory>())

    init {
        runBlocking {
            categories.value = App.Repositories.transactionCategoryRepository.getAll().toMutableList()
        }
    }
}