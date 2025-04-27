package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class SingleGoalVM(id: String) : ViewModel() {
    val goal: MutableStateFlow<GoalEntity?> = MutableStateFlow(null)
    val transactions: MutableStateFlow<List<TransactionEntity>> = MutableStateFlow(listOf())
    init {
        runBlocking {
            goal.value = App.UseCases.getGoal.execute(id)
            transactions.value = App.UseCases.getTransactionsByGoal.execute(goal.value!!)
        }
    }
}