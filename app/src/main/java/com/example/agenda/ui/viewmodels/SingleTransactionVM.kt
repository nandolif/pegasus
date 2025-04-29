package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.BankEntity
import com.example.agenda.app.entities.GoalEntity
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.app.entities.TransactionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class SingleTransactionVM(id: String) : ViewModel() {
    val transaction: MutableStateFlow<TransactionEntity?> = MutableStateFlow(null)
    val bank = MutableStateFlow<BankEntity?>(null)
    val goal = MutableStateFlow<GoalEntity?>(null)
    val category = MutableStateFlow<TransactionCategoryEntity?>(null)
    init {
        runBlocking {
            transaction.value = App.UseCases.getTransaction.execute(id)
            bank.value = App.UseCases.getBank.execute(transaction.value!!.bankId)

            category.value = App.Repositories.transactionCategoryRepository.getById(transaction.value!!.categoryId)
            if (transaction.value!!.goalId != null) {
                goal.value = App.UseCases.getGoal.execute(transaction.value!!.goalId!!)
            }
        }
    }
}