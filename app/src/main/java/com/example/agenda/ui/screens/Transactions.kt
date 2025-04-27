package com.example.agenda.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import com.example.agenda.ui.viewmodels.TransactionsVM

@Composable
fun Transactions() {
    val vm: TransactionsVM = viewModel()
    val structureVM: StructureVM = viewModel()
    val data by vm.transactions.collectAsState()
    App.UI.title = "Transações"
    Column(modifier = Modifier.fillMaxSize()) {
        Header(structureVM)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(data) {
                Item(it)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun Item(transaction: TransactionEntity) {
    Column(modifier = Modifier.clickable {
        Navigation.navController.navigate(Navigation.SingleTransactionRoute(transaction.id!!))
    }) {
        TXT(transaction.description)
        TXT(transaction.amount.toString())
        TXT(Date.dayMonthYearToString(DayMonthYearObj(transaction.day, transaction.month, transaction.year)))
    }
}