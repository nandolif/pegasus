package com.example.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.component.cards.BankCard
import com.example.agenda.ui.component.form.CreateBankForm
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.BanksVM
import com.example.agenda.ui.viewmodels.StructureVM


object Banks {
    object Default {
        const val NAME_AND_ID = "Carteira"
    }
}


@Preview(showBackground = true)
@Composable
fun Banks() {
    val vm: BanksVM = viewModel()
    val data by vm.banks.collectAsState()
    val toggleCreateBankForm = CreateBankForm(callback = { vm.fetchData() })

    val totalBalance = data.sumOf { it.balance }
    val totalCreditLimit = data.sumOf { it.creditLimit ?: 0.0 }
    val totalCreditSpend = data.sumOf { it.creditSpent ?: 0.0 }

    Structure.Wrapper(
        header = { Structure.Header("Bancos") },
        bottom = { Structure.BottomBar(toggleCreateBankForm) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.Colors.A.color)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TXT("Saldo Total: " + Money.resolve(totalBalance).text)
            TXT("Crédito Disponível Total: " + Money.resolve(totalCreditLimit).text)
            TXT("Crédito Gasto Total: " + Money.resolve(totalCreditSpend).text)

            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(data) {
                    BankCard(it) {
                        Navigation.navController.navigate(
                            Navigation.SingleBankRoute(
                                it.id!!
                            )
                        )
                    }
                }
            }
        }
    }
}
