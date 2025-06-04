package com.example.agenda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.component.cards.TransactionCategoryCard
import com.example.agenda.ui.component.cards.TransactionCategoryGrid
import com.example.agenda.ui.component.form.CreateTransactionCategoryForm
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable


object TransactionCategories {
    object Default {
        object Goal {
            const val NAME_AND_ID = "Metas"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object Transport {
            const val NAME_AND_ID = "Transporte"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object GroceryStore {
            const val NAME_AND_ID = "Mercado"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object OthersExpense {
            const val ID = "Outros Expense"
            const val NAME = "Outros"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object OthersIncome {
            const val ID = "Outros Income"
            const val NAME = "Outros"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object OthersTransfer {
            const val ID = "Outros Transfer"
            const val NAME = "Outros"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

    }

    object Screens {
        object AllTransactionCategories {
            @Serializable
            class Route
            class VM : ViewModel() {

                val expenseCategories = MutableStateFlow(mutableListOf<TransactionCategory>())
                val incomeCategories = MutableStateFlow(mutableListOf<TransactionCategory>())
                val transferCategories = MutableStateFlow(mutableListOf<TransactionCategory>())


                fun fetchData() {
                    runBlocking {
                        expenseCategories.value =
                            App.Repositories.transactionCategoryRepository.getExpenseCategories()
                                .toMutableList()
                        incomeCategories.value =
                            App.Repositories.transactionCategoryRepository.getIncomeCategories()
                                .toMutableList()
                        transferCategories.value =
                            App.Repositories.transactionCategoryRepository.getTransferCategories()
                                .toMutableList()
                    }
                }

                init {
                    fetchData()
                }
            }

            @Composable
            fun Screen() {
                val vm: VM = viewModel()
                val expenseCategories by vm.expenseCategories.collectAsState()
                val incomeCategories by vm.incomeCategories.collectAsState()
                val transferCategories by vm.transferCategories.collectAsState()


                val toggleCreateForm = CreateTransactionCategoryForm(callback = {
                        vm.fetchData()
                })

                Structure.Wrapper(
                    header = { Structure.Header("Categorias de Transações") },
                    bottom = { Structure.BottomBar({ toggleCreateForm() }) }) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        TransactionCategoryGrid(Transactions.Type.EXPENSE.value, expenseCategories)
                        TransactionCategoryGrid(Transactions.Type.INCOME.value, incomeCategories)
                        TransactionCategoryGrid(
                            Transactions.Type.TRANSFER.value,
                            transferCategories
                        )
                    }
                }
            }
        }

        object SingleTransactionCategory {
            @Serializable
            data class Route(val id: String)

            class VM : ViewModel() {
            }

            @Composable
            fun Screen(args: Route) {
                var transactionCategory by remember {
                    mutableStateOf<TransactionCategory?>(
                        null
                    )
                }

                runBlocking {
                    transactionCategory =
                        App.Repositories.transactionCategoryRepository.getById(args.id)
                }


                val createTransactionCategoryForm =
                    CreateTransactionCategoryForm(
                        id = transactionCategory?.id,
                        callback = {  }
                    )

                Structure.Wrapper(header = { Structure.Header("Categoria de Transação") }) {
                    Column {
                        Box(Modifier.background(color = Color(transactionCategory?.backgroundColor!!.toULong()))) {

                            TXT(
                                s = "Nome: ${transactionCategory?.name}",
                                color = Color(transactionCategory?.textColor!!.toULong())
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            BTN(text = "Deletar", onClick = {
                                runBlocking {
                                    App.Repositories.transactionCategoryRepository.delete(
                                        transactionCategory!!
                                    )
                                    App.Repositories.transactionRepository.deleteByCategory(
                                        transactionCategory!!.id!!
                                    )
                                }
                                Navigation.navController.navigate(AllTransactionCategories.Route())
                            })
                            BTN(onClick = createTransactionCategoryForm, text = "Atualizar")
                        }
                    }
                }
            }
        }
    }

}
