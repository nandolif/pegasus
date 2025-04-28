package com.example.agenda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.BanksVM
import com.example.agenda.ui.viewmodels.StructureVM

@Preview(showBackground = true)
@Composable
fun Banks() {
    val structureVM: StructureVM = viewModel()
    val vm: BanksVM = viewModel()
    val data by vm.banks.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Theme.Colors.A.color)) {
        Header(structureVM)
        App.UI.title = "Bancos"

        if (data.isEmpty()) {
            BTN(onClick = {
                Navigation.navController.navigate(Navigation.CreateBankRoute())
            }) { TXT(s = "Criar Banco", color = Theme.Colors.A.color) }
        } else {

            LazyColumn(Modifier.fillMaxSize()) {
                items(data) { bank ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Theme.Colors.B.color)
                            .padding(4.dp)
                            .clickable {
                                Navigation.navController.navigate(Navigation.SingleBankRoute(bank.id!!))
                            }) {
                        TXT("${bank.name}: ${Money.format(bank.balance)}")
                    }
                }
            }
        }
    }
}
