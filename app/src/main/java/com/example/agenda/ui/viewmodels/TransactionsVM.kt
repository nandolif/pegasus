package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App

class TransactionsVM : ViewModel() {
    val transactions = App.UI.cache.getTransactions()
    val banks = App.UI.cache.getBanks()
    val goals = App.UI.cache.getGoals()

}