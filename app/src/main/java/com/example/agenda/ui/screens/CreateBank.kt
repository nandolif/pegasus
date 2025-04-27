package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.BanksVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateBank(id: String? = null) {
    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(0f) }
    
    
    LaunchedEffect(Unit) {
        if (id != null) {
            runBlocking {
                val bank = App.UseCases.getBankRaw.execute(id)
                name = bank.name
                balance = bank.balance
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TXT("Criar Banco")
        Spacer(Modifier.height(8.dp))
        OTF(value = name, onValueChange = { name = it }, label = "Nome")
        OTF(
            value = balance.toString(),
            onValueChange = { balance = it.toFloatOrNull() ?: 0f },
            label = "Saldo",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(16.dp))
        BTN(onClick = {
            if (name.isEmpty()) return@BTN
            runBlocking {

                if (id == null) {
                    App.UseCases.createBank.execute(
                        Bank(
                            name = name,
                            balance = balance,
                            id = null,
                            created_at = null,
                            updated_at = null
                        )
                    )
                } else {
                    val b = App.UseCases.getBankRaw.execute(id)
                    App.UseCases.updateBank.execute(
                        Bank(
                            name = name,
                            balance = balance,
                            id = b.id,
                            created_at = b.created_at,
                            updated_at = App.Time.now()
                        )
                    )
                }
                Navigation.navController.navigate(Navigation.BanksRoute())
            }
        }) {
            TXT("Salvar", color = Theme.Colors.A.color)
        }
    }
}