package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.screens.Transactions
import kotlinx.coroutines.runBlocking

interface TransactionCategoryRepository : CRUDRepository<TransactionCategory> {

    suspend fun getExpenseCategories(): List<TransactionCategory>
    suspend fun getIncomeCategories(): List<TransactionCategory>
    suspend fun getTransferCategories(): List<TransactionCategory>

    fun setup() {
        _setup(
            TransactionCategories.Default.Goal.NAME_AND_ID,
            name = TransactionCategories.Default.Goal.NAME_AND_ID,
            backgroundColor = TransactionCategories.Default.Goal.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.Goal.TEXT_COLOR,
            type = Transactions.Type.EXPENSE
        )
        _setup(
            TransactionCategories.Default.OthersExpense.ID,
            TransactionCategories.Default.OthersExpense.NAME,
            backgroundColor = TransactionCategories.Default.OthersExpense.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.OthersExpense.TEXT_COLOR,
            type = Transactions.Type.EXPENSE
        )
        _setup(
            TransactionCategories.Default.Transport.NAME_AND_ID,
            TransactionCategories.Default.Transport.NAME_AND_ID,
            backgroundColor = TransactionCategories.Default.OthersExpense.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.OthersExpense.TEXT_COLOR,
            type = Transactions.Type.EXPENSE
        )
        _setup(
            TransactionCategories.Default.GroceryStore.NAME_AND_ID,
            TransactionCategories.Default.GroceryStore.NAME_AND_ID,
            backgroundColor = TransactionCategories.Default.OthersExpense.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.OthersExpense.TEXT_COLOR,
            type = Transactions.Type.EXPENSE
        )





        _setup(
            TransactionCategories.Default.OthersIncome.ID,
            TransactionCategories.Default.OthersIncome.NAME,
            backgroundColor = TransactionCategories.Default.OthersIncome.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.OthersIncome.TEXT_COLOR,
            type = Transactions.Type.INCOME
        )
        _setup(
            TransactionCategories.Default.OthersTransfer.ID,
            TransactionCategories.Default.OthersTransfer.NAME,
            backgroundColor = TransactionCategories.Default.OthersTransfer.BACKGROUND_COLOR,
            textColor = TransactionCategories.Default.OthersTransfer.TEXT_COLOR,
            type = Transactions.Type.TRANSFER
        )
    }

    private fun _setup(id: String, name: String,backgroundColor: String, textColor: String, type: Transactions.Type) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    TransactionCategory(
                        id = id, name = name,
                        created_at = null,
                        updated_at = null,
                        textColor = textColor,
                        backgroundColor = backgroundColor,
                        type = type
                    )
                )
            }
        }
    }
}