package com.example.agenda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import com.example.agenda.ui.viewmodels.TransactionsVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable


@Preview(showBackground = true)
@Composable
fun Test() {
    Transactions.Screens.MonthlyTransactions.Screen()
}

object Transactions {


    object Screens {

        object MonthlyTransactions {

            data class Data(
                val total: Float,
                val percentage: Double,
                val category: TransactionCategoryEntity,
                val transactions: List<TransactionEntity>,
            )

            @Serializable
            class Route

            class VM : ViewModel() {
                val transactions = MutableStateFlow(mutableListOf<TransactionEntity>())
                val currentDate = MutableStateFlow(App.Time.today)
                val allTransactionCategories =
                    MutableStateFlow(mutableListOf<TransactionCategoryEntity>())
                val data = MutableStateFlow(mutableListOf<Data>())
                val total = MutableStateFlow(0f)

                suspend fun getTransactionsByMonth(monthDate: DayMonthYearObject) {
                    transactions.value =
                        App.Repositories.transactionRepository.getByMonthAndYear(monthDate)
                            .toMutableList()
                    total.value = transactions.value.fold(0f) { acc, i -> acc + i.amount }

                    for (category in allTransactionCategories.value) {
                        var totalAmount = 0f
                        for (transaction in transactions.value) {
                            if (transaction.categoryId == category.id) {
                                totalAmount += transaction.amount
                            }
                        }

                        if (totalAmount > 0) {
                            val allTransactions =
                                transactions.value.filter { it.categoryId == category.id }
                            data.value.add(
                                Data(
                                    total = totalAmount,
                                    percentage = ((totalAmount / total.value) * 100).toDouble(),
                                    category = category,
                                    transactions = allTransactions
                                )
                            )
                        }
                    }
                }


                init {
                    runBlocking {
                        getTransactionsByMonth(currentDate.value)
                        allTransactionCategories.value =
                            App.Repositories.transactionCategoryRepository.getAll().toMutableList()
                    }
                }
            }

            @Composable
            fun Screen() {
                val structureVM: StructureVM = viewModel()
                val vm: VM = viewModel()
                val transactions by vm.transactions.collectAsState()
                val total by vm.total.collectAsState()
                val data by vm.data.collectAsState()
                App.UI.title = "Transações"
                val pagerState = rememberPagerState(
                    initialPage = TODO(),
                    initialPageOffsetFraction = TODO(),
                    pageCount = TODO()
                )
                Column {
                    Header(structureVM)
                    HorizontalPager(
                        state = TODO()
                    ) { }
                }
            }
        }
    }
}
