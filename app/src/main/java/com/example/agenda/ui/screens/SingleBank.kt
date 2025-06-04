package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import com.example.agenda.app.App
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.form.CreateBankForm
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.runBlocking

@Composable
fun SingleBank(id: String) {

    val toggleCreateBankForm = CreateBankForm(id, callback = {})
    Structure.Wrapper(
        header = { Structure.Header("Banco") },
        bottom = { Structure.BottomBar(toggleCreateBankForm, Icons.Default.Edit) }) {
        Column {
            BTN(onClick = {
                runBlocking {
                    val bank = App.UseCases.getBank.execute(id)
                    App.UseCases.deleteBank.execute(bank)
                    Navigation.navController.navigate(Navigation.BanksRoute())
                }
            }, text = "Deletar")
        }
    }
}