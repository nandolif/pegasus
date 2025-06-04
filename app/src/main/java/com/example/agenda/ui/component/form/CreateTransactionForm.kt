package com.example.agenda.ui.component.form

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.helpers.Parcel
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.Person
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BTNType
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.DateDialog
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.screens.Transactions
import com.example.agenda.ui.viewmodels.TransactionsVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateTransactionForm(id: String? = null, callback: () -> Unit): () -> Unit {
    val vm: TransactionsVM = viewModel()

    val banks by vm.banks.collectAsState()
    val transactionsTypes =
        listOf(Transactions.Type.EXPENSE, Transactions.Type.INCOME, Transactions.Type.TRANSFER)
    val transactionsBalanceType =
        listOf(Transactions.BalanceType.DEBIT, Transactions.BalanceType.CREDIT)
    val parcelRecurrence = listOf(
        Parcel.Recurrence.Monthly,
        Parcel.Recurrence.SemiMonthly,
        Parcel.Recurrence.BiMonthly,
        Parcel.Recurrence.TriMonthly,
        Parcel.Recurrence.QadriMonthly
    )
    val actualTransaction = vm.getTransaction(id)
    val parcelValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 24, 36, 48, 56, 72)
    val expenseCategories by vm.expenseCategories.collectAsState()
    val incomeCategories by vm.incomeCategories.collectAsState()
    val transferCategories by vm.transferCategories.collectAsState()
    val goals by vm.goals.collectAsState()
    val persons by vm.persons.collectAsState()

    var bankSelected by remember { mutableStateOf<Bank?>(banks.find { it.id == actualTransaction?.bankId }) }
    var amount by remember { mutableStateOf(Money.resolve(actualTransaction?.amount?: Money.ZERO)) }
    var amountInput by remember {
        mutableStateOf(
            TextFieldValue(
                text = amount.text,
                selection = TextRange(amount.text.length)
            )
        )
    }

    var description by remember { mutableStateOf(actualTransaction?.description?:"") }

    var transactionBalanceTypeSelected by remember { mutableStateOf<Transactions.BalanceType?>(
        if(actualTransaction?.isCredit == true) Transactions.BalanceType.CREDIT
        else if(actualTransaction != null && actualTransaction.isCredit == false) Transactions.BalanceType.DEBIT else null

    ) }
    var fees by remember { mutableStateOf(Money.resolve(Money.ZERO)) }
    var feesInput by remember {
        mutableStateOf(
            TextFieldValue(
                text = fees.text,
                selection = TextRange(fees.text.length)
            )
        )
    }

    var parcelValueSelected by remember { mutableStateOf<Int?>(null) }
    var parcelRecurrenceSelected by remember { mutableStateOf<Parcel.Recurrence?>(null) }
    var transactionTypeSelected by remember { mutableStateOf<Transactions.Type?>(null) }
    var bankTransferToSelected by remember { mutableStateOf<Bank?>(null) }
    var bankTransferToBalanceTypeSelected by remember {
        mutableStateOf<Transactions.BalanceType?>(
            null
        )
    }

    var categorySelected by remember { mutableStateOf<TransactionCategory?>(null) }
    var goalSelected by remember { mutableStateOf<Goal?>(null) }
    var personSelected by remember { mutableStateOf<Person?>(null) }
    val dateSelected = remember { mutableStateOf(App.Time.today) }

    val scrollState = rememberScrollState()
    val toggleDate = DateDialog.Component(dateSelected)


    fun create() {
        if (categorySelected == null ||
            bankSelected == null ||
            amount.value == Money.ZERO ||
            description.isEmpty() ||
            transactionTypeSelected == null ||
            transactionBalanceTypeSelected == null
        ) {
            return
        }

        var nMonths = when (parcelRecurrenceSelected) {
            Parcel.Recurrence.Monthly -> 1
            Parcel.Recurrence.BiMonthly -> 2
            Parcel.Recurrence.TriMonthly -> 3
            Parcel.Recurrence.QadriMonthly -> 4
            else -> null
        }
        var nDays = when (parcelRecurrenceSelected) {
            Parcel.Recurrence.SemiMonthly -> 15
            else -> null
        }
        var recurrenceType = when (parcelRecurrenceSelected) {
            Parcel.Recurrence.Monthly -> RECURRENCE.EVERY_N_MONTH_LAST_DAY
            Parcel.Recurrence.SemiMonthly -> RECURRENCE.EVERY_N_DAYS
            Parcel.Recurrence.BiMonthly -> RECURRENCE.EVERY_N_MONTH_LAST_DAY
            Parcel.Recurrence.TriMonthly -> RECURRENCE.EVERY_N_MONTH_LAST_DAY
            Parcel.Recurrence.QadriMonthly -> RECURRENCE.EVERY_N_MONTH_LAST_DAY
            else -> null
        }


        if (transactionBalanceTypeSelected == Transactions.BalanceType.DEBIT) {
            nMonths = null
            nDays = null
            recurrenceType = null
        }
        val goalId =
            if (transactionTypeSelected == Transactions.Type.EXPENSE) goalSelected?.id else null
        val categoryId =
            if (goalSelected != null) TransactionCategories.Default.Goal.NAME_AND_ID else categorySelected?.id!!
        val sign = if (transactionTypeSelected == Transactions.Type.INCOME) 1 else -1
        val transaction = Transaction(
            id = actualTransaction?.id,
            day = dateSelected.value.day,
            month = dateSelected.value.month,
            year = dateSelected.value.year,
            amount = amount.value * sign,
            description = description,
            created_at = actualTransaction?.created_at,
            updated_at = if(actualTransaction != null) App.Time.now() else null,
            bankId = bankSelected?.id!!,
            recurrenceId = actualTransaction?.recurrenceId,
            ghost = actualTransaction?.ghost ?: false,
            goalId = goalSelected?.id,
            canceled = actualTransaction?.canceled ?: false,
            canceledDay = actualTransaction?.canceledDay,
            canceledMonth = actualTransaction?.canceledMonth,
            canceledYear = actualTransaction?.canceledYear,
            nDays = nDays,
            nWeeks = null,
            nMonths = nMonths,
            nYears = null,
            recurrenceType = recurrenceType,
            categoryId = categoryId,
            personId = personSelected?.id,
            isCredit = transactionBalanceTypeSelected == Transactions.BalanceType.CREDIT,
            type = transactionTypeSelected!!
        )

        val transferTo = if (transactionTypeSelected == Transactions.Type.TRANSFER) {
            Transaction(
                id = null,
                day = dateSelected.value.day,
                month = dateSelected.value.month,
                year = dateSelected.value.year,
                amount = (amount.value * sign) * -1,
                description = description,
                created_at = null,
                updated_at = null,
                bankId = bankTransferToSelected?.id!!,
                recurrenceId = null,
                ghost = false,
                goalId = goalId,
                canceled = false,
                canceledDay = null,
                canceledMonth = null,
                canceledYear = null,
                nDays = nDays,
                nWeeks = null,
                nMonths = nMonths,
                nYears = null,
                recurrenceType = recurrenceType,
                categoryId = categoryId,
                personId = personSelected?.id,
                isCredit = bankTransferToBalanceTypeSelected == Transactions.BalanceType.CREDIT,
                type = transactionTypeSelected!!
            )
        } else null

        runBlocking {
            App.UseCases.createTransaction.execute(transaction)
            if (transferTo != null) App.UseCases.createTransaction.execute(transferTo)
            callback()
        }
        description = ""
        amount = Money.resolve(Money.ZERO)
        dateSelected.value = App.Time.today
        transactionTypeSelected = null
        transactionBalanceTypeSelected = null
        categorySelected = null
        goalSelected = null
        personSelected = null
        bankSelected = null
        bankTransferToSelected = null
        bankTransferToBalanceTypeSelected = null
        parcelRecurrenceSelected = null
        parcelValueSelected = null
        fees = Money.resolve(Money.ZERO)
        amountInput = TextFieldValue(text = amount.text, selection = TextRange(amount.text.length))
        feesInput = TextFieldValue(text = fees.text, selection = TextRange(fees.text.length))

    }

    val toggle = BottomSheet.Wrapper { t ->

        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            TextField(
                value = description,
                label = { TXT("Descrição") },
                onValueChange = { description = it })

            TextField(
                value = amountInput,
                label = { TXT("Valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    amount = Money.resolve(it.text)
                    amountInput =
                        amountInput.copy(
                            text = amount.text,
                            selection = TextRange(amount.text.length)
                        )
                })

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.horizontalScroll(scrollState)
            ) {
                BTN(Date.dayMonthYearToString(dateSelected.value), toggleDate)
                BTN("Hoje", {
                    dateSelected.value = App.Time.today
                })
                BTN("Ontem", {
                    dateSelected.value =
                        Date.getDate(App.Time.today.copy(day = App.Time.today.day - 1))
                })
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(transactionsBalanceType) {
                    BTN(
                        it.value,
                        onClick = { transactionBalanceTypeSelected = it },
                        type = if (transactionBalanceTypeSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                    )
                }
            }

            if (transactionBalanceTypeSelected != null && transactionBalanceTypeSelected == Transactions.BalanceType.CREDIT) {
                TextField(
                    value = feesInput,
                    label = { TXT("Juros") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        fees = Money.resolve(it.text)
                        feesInput =
                            feesInput.copy(
                                text = fees.text,
                                selection = TextRange(fees.text.length)
                            )
                    })

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(parcelValues) {
                        BTN(
                            "${it}X",
                            onClick = { parcelValueSelected = it },
                            type = if (parcelValueSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                        )
                    }
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(parcelRecurrence) {
                        BTN(
                            it.type,
                            onClick = { parcelRecurrenceSelected = it },
                            type = if (parcelRecurrenceSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                        )
                    }
                }
            }

            val bankList =
                if (transactionBalanceTypeSelected == Transactions.BalanceType.CREDIT) banks.filter { it.creditLimit != null } else banks
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(bankList) {
                    BTN(
                        "${it.name} - Saldo: ${it.balance} / Crédito Gasto: ${it.creditSpent}\" / Limite de Crédito: ${it.creditLimit}",
                        onClick = { bankSelected = it },
                        type = if (bankSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                    )
                }
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(transactionsTypes) {
                    BTN(
                        it.value,
                        onClick = { transactionTypeSelected = it },
                        type = if (transactionTypeSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                    )
                }
            }

            if (transactionTypeSelected != null && transactionTypeSelected == Transactions.Type.TRANSFER && transactionBalanceTypeSelected == Transactions.BalanceType.DEBIT) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(banks.filter { it != bankSelected }) {
                        BTN(
                            "${it.name} - Saldo: ${it.balance} / Limite: ${it.creditLimit} / Crédito Gasto: ${it.creditSpent}",
                            onClick = { bankTransferToSelected = it },
                            type = if (bankTransferToSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                        )
                    }
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(transactionsBalanceType) {
                        BTN(
                            it.value,
                            onClick = { bankTransferToBalanceTypeSelected = it },
                            type = if (bankTransferToBalanceTypeSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                        )
                    }
                }
            }
            val categories =
                if (transactionTypeSelected == Transactions.Type.EXPENSE) expenseCategories else if (transactionTypeSelected == Transactions.Type.INCOME) incomeCategories else transferCategories
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(categories) {
                    BTN(
                        it.name,
                        onClick = { categorySelected = it },
                        type = if (categorySelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                    )
                }
            }

            if (transactionTypeSelected == Transactions.Type.EXPENSE && transactionBalanceTypeSelected == Transactions.BalanceType.DEBIT) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(goals) {
                        BTN(
                            "${it.title} - Valor Atual: ${it.amount} - Valor Total: ${it.actualAmount} - Alcançado: ${it.achieved}",
                            onClick = { goalSelected = it },
                            type = if (goalSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                        )
                    }
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(persons) {
                    BTN(
                        it.name,
                        onClick = {
                            if (personSelected != it) {
                                personSelected = it
                            } else {
                                personSelected = null
                            }
                        },
                        type = if (personSelected == it) BTNType.PRIMARY else BTNType.SECONDARY
                    )
                }
            }

            BTN("Criar Transação", {
                create()
                t()
            })
        }
    }
    return { toggle() }
}
