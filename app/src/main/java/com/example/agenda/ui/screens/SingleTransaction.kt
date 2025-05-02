package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.SingleTransactionVM
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.runBlocking

@Composable
fun SingleTransaction(id: String) {
    val vm: SingleTransactionVM = SingleTransactionVM(id)
    val structureVM: StructureVM = viewModel()
    val transaction by vm.transaction.collectAsState()
    val bank by vm.bank.collectAsState()
    val goal by vm.goal.collectAsState()
    val category by vm.category.collectAsState()
    Column {
        Header(structureVM)
        if (transaction == null) {
            TXT("Cargando...")
        } else {

            Column {
                TXT(transaction!!.description)
                TXT(Money.format(transaction!!.amount))
                TXT(bank!!.name)
                TXT("Categoria: "+category!!.name)
                if (goal != null) {
                    TXT(goal!!.title)
                }

                BTN(onClick = {
                    runBlocking {
                        App.UseCases.deleteTransaction.execute(transaction!!)
                    }
                    Navigation.navController.navigate(Navigation.TransactionsRoute())
                },text="Deletar")

                BTN(onClick = {
                    Navigation.navController.navigate(Navigation.CreateTransactionRoute(transaction!!.id!!))
                }, text="Atualizar")

            }
        }
    }
}