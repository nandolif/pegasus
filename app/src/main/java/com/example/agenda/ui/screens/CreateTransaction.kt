package com.example.agenda.ui.screens

import Money
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.DateDialog
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import com.example.agenda.ui.viewmodels.TransactionsVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateTransaction(id: String? = null) {
    var amount by remember { mutableStateOf(0f) }
    var description by remember { mutableStateOf("") }
    var bankId by remember { mutableStateOf("") }
    var goalId by remember { mutableStateOf<String?>(null) }
    var categoryId by remember { mutableStateOf<String>("") }
    var date by remember { mutableStateOf<DayMonthYearObject?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val structureVM: StructureVM = viewModel()
    val vm: TransactionsVM = viewModel()
    val banks by vm.banks.collectAsState()
    val goals by vm.goals.collectAsState()
    val categories by vm.categories.collectAsState()

    fun onDismiss() {
        isDialogVisible = !isDialogVisible
    }

    fun onAccept(accept: Long) {
        date = Date.longToDayMonthYear(accept)
        onDismiss()
    }

    LaunchedEffect(Unit) {

        if (id != null) {
            val transaction = App.UseCases.getTransaction.execute(id)
            amount = transaction.amount
            description = transaction.description
            date = DayMonthYearObj(transaction.day, transaction.month, transaction.year)
            bankId = transaction.bankId
            categoryId = transaction.categoryId
            if (transaction.goalId != null) {
                goalId = transaction.goalId!!
            }
        }
    }

    Column {

        Header(structureVM)
        DateDialog(
            isDialogVisible = isDialogVisible,
            onAccept = { onAccept(it) },
            onDismiss = { onDismiss() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OTF(
                value = description,
                onValueChange = { description = it },
                label = "Descrição"
            )
            OTF(
                value = amount.toString(),
                onValueChange = { amount = it.toFloatOrNull() ?: 0f },
                label = "Valor",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            BTN(onClick = {
                isDialogVisible = !isDialogVisible
            }) {
                val text =
                    if (date == null) "Selecione uma data" else Date.dayMonthYearToString(date!!)
                TXT(s = text, color = Theme.Colors.A.color)
            }
            Spacer(Modifier.height(6.dp))
            TXT(s = "Bancos")
            LazyColumn {
                items(banks) {
                    Row {
                        TXT(s = it.name)
                        TXT(s = Money.format(it.balance))
                        val isSelected = bankId == it.id
                        val text = if (isSelected) "Desmarcar" else "Marcar"
                        val buttonColors = if (isSelected) {
                            ButtonColors(
                                containerColor = Theme.Colors.A.color,
                                contentColor = Theme.Colors.A.color,
                                disabledContainerColor = Theme.Colors.A.color,
                                disabledContentColor = Theme.Colors.A.color,
                            )
                        } else {
                            ButtonColors(
                                containerColor = Theme.Colors.D.color,
                                contentColor = Theme.Colors.D.color,
                                disabledContainerColor = Theme.Colors.D.color,
                                disabledContentColor = Theme.Colors.D.color,
                            )
                        }
                        val textColor =
                            if (isSelected) Theme.Colors.D.color else Theme.Colors.A.color
                        BTN(onClick = {
                            if (bankId == it.id) {
                                bankId = ""
                            } else {
                                bankId = it.id!!
                            }
                        }, buttonColors = buttonColors) {
                            TXT(s = text, color = textColor)
                        }
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            TXT(s = "Objetivos")
            LazyColumn {
                items(goals) {
                    Row {
                        Column {
                            TXT(s = it.title)
                            TXT(s = "Valor total: "+Money.format(it.amount))
                            TXT(s = "Valor adquirido: "+Money.format(it.actualAmount!!))
                        }
                        val isSelected = goalId == it.id
                        val text = if (isSelected) "Desmarcar" else "Marcar"
                        val buttonColors = if (isSelected) {
                            ButtonColors(
                                containerColor = Theme.Colors.A.color,
                                contentColor = Theme.Colors.A.color,
                                disabledContainerColor = Theme.Colors.A.color,
                                disabledContentColor = Theme.Colors.A.color,
                            )
                        } else {
                            ButtonColors(
                                containerColor = Theme.Colors.D.color,
                                contentColor = Theme.Colors.D.color,
                                disabledContainerColor = Theme.Colors.D.color,
                                disabledContentColor = Theme.Colors.D.color,
                            )
                        }
                        val textColor =
                            if (isSelected) Theme.Colors.D.color else Theme.Colors.A.color
                        BTN(onClick = {
                            if (goalId == it.id) {
                                goalId = ""
                            } else {
                                goalId = it.id!!
                            }
                        }, buttonColors = buttonColors) {
                            TXT(s = text, color = textColor)
                        }
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            TXT(s = "Categorias")
            LazyColumn {
                items(categories) {
                    Row {
                        Column {
                            TXT(s = it.name)
                        }
                        val isSelected = categoryId == it.id
                        val text = if (isSelected) "Desmarcar" else "Marcar"
                        val buttonColors = if (isSelected) {
                            ButtonColors(
                                containerColor = Theme.Colors.A.color,
                                contentColor = Theme.Colors.A.color,
                                disabledContainerColor = Theme.Colors.A.color,
                                disabledContentColor = Theme.Colors.A.color,
                            )
                        } else {
                            ButtonColors(
                                containerColor = Theme.Colors.D.color,
                                contentColor = Theme.Colors.D.color,
                                disabledContainerColor = Theme.Colors.D.color,
                                disabledContentColor = Theme.Colors.D.color,
                            )
                        }
                        val textColor =
                            if (isSelected) Theme.Colors.D.color else Theme.Colors.A.color
                        BTN(onClick = {
                            if (categoryId == it.id) {
                                categoryId = ""
                            } else {
                                categoryId = it.id!!
                            }
                        }, buttonColors = buttonColors) {
                            TXT(s = text, color = textColor)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            BTN(onClick = {
                if (date == null) return@BTN
                if (amount == 0f) return@BTN
                if (description.isEmpty()) return@BTN
                if (bankId.isEmpty()) return@BTN
                if(categoryId.isEmpty()) return@BTN
                if (id == null) {
                    val transaction = Transaction(
                        id = null,
                        day = date!!.day,
                        month = date!!.month,
                        year = date!!.year,
                        amount = amount,
                        description = description,
                        created_at = null,
                        updated_at = null,
                        bankId = bankId,
                        recurrenceId = null,
                        ghost = false,
                        goalId = goalId,
                        canceled = false,
                        canceledDay = null,
                        canceledMonth = null,
                        canceledYear = null,
                        nDays = null,
                        nWeeks = null,
                        nMonths = null,
                        nYears = null,
                        recurrenceType = null,
                        categoryId = categoryId
                    )
                    runBlocking {
                        App.UseCases.createTransaction.execute(transaction)
                    }
                } else {
                    runBlocking {
                        val transaction = App.UseCases.getTransaction.execute(id)
                        val t = Transaction(
                            id = transaction.id,
                            day = date!!.day,
                            month = date!!.month,
                            year = date!!.year,
                            amount = amount,
                            description = description,
                            created_at = transaction.created_at,
                            updated_at = App.Time.now(),
                            bankId = bankId,
                            recurrenceId = transaction.recurrenceId,
                            ghost = transaction.ghost,
                            goalId = goalId,
                            canceled = transaction.canceled,
                            canceledDay = transaction.canceledDay,
                            canceledMonth = transaction.canceledMonth,
                            canceledYear = transaction.canceledYear,
                            nDays = transaction.nDays,
                            nWeeks = transaction.nWeeks,
                            nMonths = transaction.nMonths,
                            nYears = transaction.nYears,
                            recurrenceType = transaction.recurrenceType,
                            categoryId = categoryId
                        )

                        App.UseCases.updateTransaction.execute(t)
                    }
                }
                Navigation.navController.navigate(Navigation.TransactionsRoute())
            }) {
                TXT(s = "Salvar", color = Theme.Colors.A.color)
            }
        }
    }
}
