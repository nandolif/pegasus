package com.example.agenda.ui.screens

import Money
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.helps.Calculator
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.TransactionWithData
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.Pager
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.component.cards.MonthCategoryTransactionList
import com.example.agenda.ui.component.cards.TransactionCard
import com.example.agenda.ui.component.form.CreateTransactionForm
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

object Transactions {
    enum class Type(val icon: ImageVector, val value: String, val color: Color) {
        INCOME(Icons.Default.ArrowUpward, "Renda", Color(0xFF429345)),
        EXPENSE(Icons.Default.ArrowDownward, "Despesa", Color(0xFF983333)),
        TRANSFER(Icons.Default.Recycling, "Transferência", Color(0xFF336B8C)),
    }

    enum class BalanceType(val value: String) {
        CREDIT("Crédito"),
        DEBIT("Débito"),
    }

    object Screens {
        object MonthlyTransactions {

            @Serializable
            class Route

            class VM : ViewModel() {
                val data = MutableStateFlow<MutableMap<DayMonthYearObj, List<TransactionWithData>>>(
                    mutableMapOf()
                )

                suspend fun resetAndFetchPageData(date: DayMonthYearObj): List<TransactionWithData> {
                    data.value = mutableMapOf()
                    return fetchPageData(date)
                }

                suspend fun fetchPageData(data: DayMonthYearObj): List<TransactionWithData> {
                    val transactionList =
                        App.Repositories.transactionRepository.getByMonthAndYearWithData(data)
                    return transactionList
                }

                init {
                    runBlocking {
                        data.value[App.Time.today] = fetchPageData(App.Time.today)
                    }
                }
            }

            @Composable
            fun Screen() {
                val vm: VM = viewModel()
                val coroutineScope = rememberCoroutineScope()
                val toggleCreateTransactionForm =
                    CreateTransactionForm(callback = {
                        coroutineScope.launch {
                            vm.resetAndFetchPageData(
                                App.Time.today
                            )
                        }
                    })
                val currentDate = remember { mutableStateOf(App.Time.today) }
                val data by vm.data.collectAsState()
                val cp = data[currentDate.value]
                val listExpense = cp?.filter { it.category.type == Type.EXPENSE } ?: listOf()
                val listIncome = cp?.filter { it.category.type == Type.INCOME } ?: listOf()
                val total =
                    cp?.fold(Money.ZERO) { acc, i -> acc + i.transaction.amount } ?: Money.ZERO


                Structure.Wrapper(
                    header = {
                        Structure.Header("Transações", actions = {
                            IconButton(onClick = {
                                Navigation.navController.navigate(
                                    TransactionCategories.Screens.AllTransactionCategories.Route()
                                )
                            }) {
                                Icon(
                                    Theme.Icons.TransactionCategory.icon,
                                    contentDescription = "Categoria de Transações"
                                )
                            }
                        })
                    },
                    bottom = { Structure.BottomBar({ toggleCreateTransactionForm() }) },
                ) {
                    Pager.Component(
                        currentDate = currentDate,
                        data = data,
                        callback = { vm.fetchPageData(currentDate.value) }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(30.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Theme.Colors.B.color,
                                        shape = RoundedCornerShape(6.dp)
                                    ),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Theme.Colors.C.color,
                                            RoundedCornerShape(
                                                topStart = 6.dp,
                                                topEnd = 6.dp
                                            )
                                        )
                                        .padding(8.dp)

                                ) {
                                    TXT("${Date.geMonthText(currentDate.value)} - ${currentDate.value.year}")
                                    TXT(
                                        "Saldo: " + Money.resolve(
                                            total,
                                            withCurrency = true
                                        ).text
                                    )
                                }
                                val incomeTotal =
                                    Calculator.round(listIncome.fold(Money.ZERO) { acc, i -> acc + i.transaction.amount })
                                val expenseTotal =
                                    Calculator.round(listExpense.fold(Money.ZERO) { acc, i -> acc + i.transaction.amount })

                                MonthCategoryTransactionList(
                                    incomeTotal,
                                    Type.INCOME,
                                    listIncome,
                                )
                                MonthCategoryTransactionList(
                                    expenseTotal,
                                    Type.EXPENSE,
                                    listExpense
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .background(
                                        Theme.Colors.B.color,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            Theme.Colors.C.color,
                                            RoundedCornerShape(
                                                topStart = 6.dp,
                                                topEnd = 6.dp
                                            )
                                        )

                                        .padding(8.dp)

                                ) {
                                    TXT("Todas as Transações deste Mês")
                                    TXT("${cp?.size ?: 0}")
                                }

                                if (cp.isNullOrEmpty()) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                Theme.Colors.B.color,
                                                RoundedCornerShape(
                                                    bottomStart = 6.dp,
                                                    bottomEnd = 6.dp
                                                )
                                            )
                                            .padding(8.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Theme.Colors.A.color,
                                                    RoundedCornerShape(6.dp)
                                                )
                                                .padding(8.dp)
                                        ) {
                                            TXT("Nenhuma Transação Registrada Neste Mês")
                                        }
                                    }
                                } else {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(3),
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        items(
                                            cp.sortedWith(
                                                compareBy(
                                                    { it.transaction.day },
                                                    { it.transaction.created_at }
                                                )
                                            ).reversed()
                                        ) {
                                            TransactionCard(it) {
                                                Navigation.navController.navigate(
                                                    Navigation.SingleTransactionRoute(it.transaction.id!!)
                                                )
                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }


                }
            }
        }

    }
}