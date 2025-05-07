package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Goal
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.GoalsVM
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateGoal(vm: GoalsVM = viewModel()) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(Money.ZERO) }
    val structureVM: StructureVM = viewModel()
    App.UI.title = "Criar Meta"
    Column {
        Header(structureVM)
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OTF(
                value = title,
                onValueChange = { title = it },
                label = "Título"
            )
            OTF(
                value = amount.toString(),
                onValueChange = { amount = it.toDoubleOrNull() ?: Money.ZERO },
                label = "Valor",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(16.dp))
            BTN(onClick = {
                if (title.isEmpty()) return@BTN
                if (amount == Money.ZERO) return@BTN

                runBlocking {
                    App.UseCases.createGoal.execute(
                        Goal(
                            title = title,
                            amount = amount,
                            id = null,
                            created_at = null,
                            updated_at = null,
                            achieved = false,
                            actualAmount = null,
                        )
                    )
                }
                Navigation.navController.navigate(Navigation.GoalsRoute())
            },text = "Salvar")
        }
    }
}
