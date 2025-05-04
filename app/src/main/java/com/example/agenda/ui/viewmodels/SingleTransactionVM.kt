package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class SingleTransactionVM(id: String) : ViewModel() {
    val transaction: MutableStateFlow<Transaction?> = MutableStateFlow(null)
    val bank = MutableStateFlow<Bank?>(null)
    val goal = MutableStateFlow<Goal?>(null)
    val category = MutableStateFlow<TransactionCategory?>(null)
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