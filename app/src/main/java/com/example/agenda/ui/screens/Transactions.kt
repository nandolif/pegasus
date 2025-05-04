package com.example.agenda.ui.screens

import Money
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.DateDialog
import com.example.agenda.ui.component.Form
import com.example.agenda.ui.component.Modal
import com.example.agenda.ui.component.Pager
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import com.example.agenda.ui.viewmodels.TransactionsVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable


object Transactions {

    enum class Type(val value: String) {
        INCOME("Renda"),
        EXPENSE("Despesa"),
        TRANSFER("Transferência"),
    }

    object Screens {
        object MonthlyTransactions {
            data class Data(
                val total: Float,
                val percentage: Double,
                val category: TransactionCategory,
                val transactions: List<Transaction>,
            )

            data class DataList(
                val date: DayMonthYearObj,
                val total: Float,
                val data: List<Data>,
            )

            class FetchMoreData(
                private val vm: VM,
            ) : Pager.FetchMoreData<DataList> {
                override suspend fun execute(
                    data: List<DataList>,
                    pagerState: PagerState,
                    last: Boolean,
                    indexOffset: MutableStateFlow<Int>,
                ) {
                    if (last) {
                        val d = data.last()
                        val date = Date.getDate(
                            DayMonthYearObj(
                                day = d.date.day,
                                month = d.date.month + 1,
                                year = d.date.year
                            )
                        )
                        vm.getTransactionsByMonth(date, true)
                        return
                    }
                    val d = data[0]
                    val date = Date.getDate(
                        DayMonthYearObj(
                            day = d.date.day,
                            month = d.date.month - 1,
                            year = d.date.year
                        )
                    )
                    vm.getTransactionsByMonth(date, false)
                    pagerState.scrollToPage(1)
                    indexOffset.value += 1
                }

            }


            @Serializable
            class Route

            class VM : ViewModel() {
                val currentDate = MutableStateFlow(App.Time.today)
                val allTransactionCategories =
                    MutableStateFlow(mutableListOf<TransactionCategory>())
                val data = MutableStateFlow<MutableList<DataList>>(mutableListOf())
                val indexOffset = MutableStateFlow(0)
                val fetchMoreData = FetchMoreData(this)

                suspend fun getTransactionsByMonth(monthDate: DayMonthYearObj, last: Boolean) {
                    val transactions =
                        App.Repositories.transactionRepository.getByMonthAndYear(monthDate)
                            .toMutableList()
                    val total = transactions.fold(0f) { acc, i -> acc + i.amount }
                    val dList: MutableList<Data> = mutableListOf()
                    for (category in allTransactionCategories.value) {
                        var totalAmount = 0f
                        val allTransactions =
                            transactions.filter { it.categoryId == category.id }
                        for (transaction in allTransactions) {
                            totalAmount += transaction.amount
                        }
                        if (totalAmount == 0f) continue

                        val d = Data(
                            total = totalAmount,
                            percentage = ((totalAmount / total) * 100).toDouble(),
                            category = category,
                            transactions = allTransactions
                        )
                        dList.add(d)
                    }
                    val datalist = DataList(monthDate, total, dList)
                    if (last) data.value.add(datalist) else data.value.add(0, datalist)
                }


                init {
                    runBlocking {
                        allTransactionCategories.value =
                            App.Repositories.transactionCategoryRepository.getAll().toMutableList()
                        val past = Date.getDate(
                            DayMonthYearObj(
                                day = currentDate.value.day,
                                month = currentDate.value.month - 1,
                                year = currentDate.value.year
                            )
                        )

                        val future = Date.getDate(
                            DayMonthYearObj(
                                day = currentDate.value.day,
                                month = currentDate.value.month + 1,
                                year = currentDate.value.year
                            )
                        )
                        getTransactionsByMonth(past, false)
                        getTransactionsByMonth(currentDate.value, true)
                        getTransactionsByMonth(future, true)

                    }
                }
            }

            @Composable
            fun Screen() {
                val structureVM: StructureVM = viewModel()
                val vm: VM = viewModel()
                val data by vm.data.collectAsState()
                val fetchMoreData = vm.fetchMoreData
                val indexOffset = vm.indexOffset
                App.UI.title = "Transações"
                val pageCount = Pager.pageCount(data.size)
                val pagerState = rememberPagerState(pageCount = { pageCount }, initialPage = 1)
                val currentPage = data.getOrNull(pagerState.currentPage)
                Column {
                    Header(structureVM)
                    Pager.Wrapper(
                        data,
                        currentPage,
                        noData = {
                            BTN(
                                "Criar Transação",
                                { Navigation.navController.navigate(CreateTransaction.Route()) })
                        },
                        content = { cp ->
                            Pager.Component(pagerState) {
                                Column {
                                    TXT(Date.geMonthText(cp.date))
                                    TXT(Money.format(cp.total))
                                    Spacer(Modifier.height(16.dp))
                                    LazyColumn {
                                        items(cp.data.sortedByDescending { it.percentage }) {
                                            TXT(it.category.name)
                                            TXT(Money.format(it.total))
                                            TXT(it.percentage.toString())
                                        }
                                    }
                                }
                            }
                            Pager.Effect(fetchMoreData, pagerState, data, indexOffset)
                        }
                    )

                }
            }
        }

        object CreateTransaction {
            @Serializable
            data class Route(val id: String? = null)


            @Composable
            fun Screen(route: Route) {
                var amount by remember { mutableStateOf(0f) }
                var description by remember { mutableStateOf("") }
                val bank = remember { mutableStateOf<Bank?>(null) }
                val transferTo = remember { mutableStateOf<Bank?>(null) }
                val goal = remember { mutableStateOf<Goal?>(null) }
                val category = remember { mutableStateOf<TransactionCategory?>(null) }
                val date = remember { mutableStateOf<DayMonthYearObj?>(null) }
                val structureVM: StructureVM = viewModel()
                val vm: TransactionsVM = viewModel()
                val banks by vm.banks.collectAsState()
                val goals by vm.goals.collectAsState()
                val categories by vm.categories.collectAsState()
                var currency by remember { mutableStateOf(Money.resolve(0f, 0f)) }
                var type = remember { mutableStateOf(Type.EXPENSE) }

                LaunchedEffect(Unit) {
                }

                fun checkIfBankHasBalance() {
                    if (
                        bank.value != null
                        && (currency.float > bank.value!!.balance)
                        && (type.value == Type.EXPENSE || type.value == Type.TRANSFER)
                    ) {
                        currency = Money.resolve(bank.value!!.balance, bank.value!!.balance)
                    }
                }
                Column {
                    Header(structureVM)
                    Form.Wrapper {
                        Form.Input(
                            Theme.Icons.Text.icon,
                            "Adicionar Descrição",
                            description,
                            { description = it }
                        )
                        Form.Input(
                            Theme.Icons.Transaction.icon,
                            placeholder = if (
                                bank.value?.balance == 0f
                                && (type.value == Type.EXPENSE || type.value == Type.TRANSFER)
                            ) {
                                "Saldo Insuficiente"
                            } else {
                                "Adicionar Valor"
                            },
                            value = if (
                                Money.isZero(currency.string)
                                || (bank.value?.balance == 0f
                                        && (type.value == Type.EXPENSE || type.value == Type.TRANSFER))
                            ) {
                                ""
                            } else {
                                currency.string
                            },
                            {
                                currency = if (Money.isZero(currency.string) && it != "") {
                                    Money.resolve(it.toFloat(), it.toFloat())
                                } else {
                                    Money.resolve(it, currency.string)
                                }

                                checkIfBankHasBalance()
                            },
                            KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        LaunchedEffect(type.value) {
                            checkIfBankHasBalance()
                        }
                        val toggleTypeModal = Modal.Wrapper("Tipo de Transação") { toggle ->
                            Modal.List<Unit> {
                                Modal.Item(
                                    state = type,
                                    id = Type.EXPENSE,
                                    callback = toggle,
                                    text = Type.EXPENSE.value
                                )
                                Modal.Item(
                                    state = type,
                                    id = Type.INCOME,
                                    callback = toggle,
                                    text = Type.INCOME.value
                                )
                                Modal.Item(
                                    state = type,
                                    id = Type.TRANSFER,
                                    callback = toggle,
                                    text = Type.TRANSFER.value
                                )
                            }
                        }
                        Form.Row(
                            Theme.Icons.Money.icon,
                            toggleTypeModal,
                            "Tipo de Transação",
                            type.value.value
                        )


                        LaunchedEffect(bank.value) {
                            checkIfBankHasBalance()
                            if (transferTo.value == bank.value) {
                                transferTo.value = null
                            }
                        }
                        val toggleBankModal = Modal.Wrapper("Selecionar Banco") { toggle ->
                            Modal.List(items = banks, extraItem = {
                                Modal.Item(
                                    state = remember { mutableStateOf(Unit) },
                                    id = Unit,
                                    callback = {
                                        Navigation.navController.navigate(
                                            Navigation.CreateBankRoute()
                                        )
                                    },
                                    text = "Criar Banco",
                                    icon = Theme.Icons.Bank.icon
                                )
                            }) {

                                Modal.Item(
                                    state = bank,
                                    id = it,
                                    callback = toggle,
                                    text = it.name,
                                    extraInfo = Money.format(it.balance, false)
                                )
                            }
                        }


                        Form.Row(
                            Theme.Icons.Bank.icon,
                            toggleBankModal,
                            "Selecionar Banco",
                            bank.value?.name,
                            extraInfo = if (bank.value != null) {
                                if (!Money.isZero(currency.string)) {
                                    val money =
                                        if (type.value == Type.TRANSFER || type.value == Type.EXPENSE) {
                                            bank.value!!.balance - currency.float
                                        } else {
                                            bank.value!!.balance + currency.float

                                        }
                                    val sign = if (money < 0) "-" else ""
                                    "${Money.format(bank.value!!.balance, false)} > $sign${
                                        Money.format(
                                            money,
                                            false
                                        )
                                    }"
                                } else {
                                    Money.format(bank.value!!.balance, false)
                                }
                            } else {
                                null
                            }
                        )
                        if (type.value == Type.TRANSFER && bank.value?.balance != 0f) {
                            val toggleTransferToModal =
                                Modal.Wrapper("Transferir para...") { toggle ->
                                    Modal.List(
                                        items = banks.filter { it.id != bank.value!!.id },
                                        extraItem = {
                                            Modal.Item(
                                                state = remember { mutableStateOf(Unit) },
                                                id = Unit,
                                                callback = {
                                                    Navigation.navController.navigate(
                                                        Navigation.CreateBankRoute()
                                                    )
                                                },
                                                text = "Criar Banco",
                                                icon = Theme.Icons.Bank.icon,
                                            )
                                        },
                                    ) {
                                        Modal.Item(
                                            state = transferTo,
                                            id = it,
                                            callback = toggle,
                                            text = it.name,
                                            extraInfo = Money.format(it.balance, false)
                                        )
                                    }
                                }
                            Form.Row(
                                Theme.Icons.Transfer.icon,
                                toggleTransferToModal,
                                "Transferir para...",
                                transferTo.value?.name,
                                extraInfo = if (transferTo.value != null) {
                                    if (!Money.isZero(currency.string)) {
                                        val money = transferTo.value!!.balance + currency.float
                                        "${Money.format(transferTo.value!!.balance, false)} > ${
                                            Money.format(
                                                money,
                                                false
                                            )
                                        }"
                                    } else {
                                        Money.format(transferTo.value!!.balance, false)
                                    }
                                } else {
                                    null
                                }
                            )
                        }
                        val toggleDateDialog = DateDialog.Component(date)
                        Form.Row(
                            Theme.Icons.Event.icon,
                            toggleDateDialog,
                            "Selecionar Data",
                            if (date.value != null) Date.dayMonthYearToString(date.value!!) else null
                        )
                        if (goal.value == null) {
                            val toggleCategoryModal =
                                Modal.Wrapper("Selecionar Categoria") { toggle ->
                                    Modal.List(
                                        items = categories.filter { it.id != TransactionCategories.Default.Goal.NAME_AND_ID },
                                        extraItem = {
                                            Modal.Item(
                                                state = remember { mutableStateOf(Unit) },
                                                id = Unit,
                                                callback = {
                                                    Navigation.navController.navigate(
                                                        TransactionCategories.Screens.CreateTransactionCategory.Route()
                                                    )
                                                },
                                                text = "Criar Categoria",
                                                icon = Theme.Icons.TransactionCategory.icon
                                            )
                                        },
                                        {
                                            Modal.Item(
                                                state = category,
                                                id = it,
                                                callback = toggle,
                                                text = it.name,
                                                boxColorEnabled = Color(it.backgroundColor.toULong()),
                                                boxColorDisabled = Color(it.textColor.toULong())
                                            )
                                        })
                                }
                            Form.Row(
                                Theme.Icons.TransactionCategory.icon,
                                toggleCategoryModal,
                                "Selecionar Categoria",
                                category.value?.name
                            )
                        }
                        if (goals.isNotEmpty()) {
                            val toggleGoalModal = Modal.Wrapper("Selecionar Meta") { toggle ->
                                Modal.List(goals) {
                                    Modal.Item(
                                        state = goal,
                                        id = it,
                                        callback = toggle,
                                        text = it.title
                                    )
                                }
                            }
                            Form.Row(
                                Theme.Icons.Goal.icon,
                                toggleGoalModal,
                                "Selecionar Meta",
                                text = goal.value?.title,
                            )
                        }
                        BTN(onClick = {}, text = "Salvar")
                    }
                }
            }
        }
    }
}
