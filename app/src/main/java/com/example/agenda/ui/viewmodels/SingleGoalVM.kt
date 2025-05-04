package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class SingleGoalVM(id: String) : ViewModel() {
    val goal: MutableStateFlow<Goal?> = MutableStateFlow(null)
    val transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(listOf())
    init {
        runBlocking {
            goal.value = App.UseCases.getGoal.execute(id)
            transactions.value = App.UseCases.getTransactionsByGoal.execute(goal.value!!)
        }
    }
}