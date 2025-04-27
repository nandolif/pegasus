package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.runBlocking

@Composable
fun SingleBank(id: String) {
    Column {
        BTN(onClick = {
            Navigation.navController.navigate(Navigation.CreateBankRoute(id))
        }) {
            TXT(s="Atualizar",color = Theme.Colors.A.color)
        }

        BTN(onClick = {
            runBlocking {
                val bank = App.UseCases.getBank.execute(id)
                App.UseCases.deleteBank.execute(bank)
                Navigation.navController.navigate(Navigation.BanksRoute())
            }
        }) { TXT(s="Delete",color = Theme.Colors.A.color) }
    }

}