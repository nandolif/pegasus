package com.example.agenda.ui.component.form


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.BankFlags
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.EDM
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import kotlinx.coroutines.runBlocking

@Composable
fun CreateBankForm(id: String? = null, callback: () -> Unit): () -> Unit {
    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(Money.ZERO) }
    var creditLimit by remember { mutableStateOf(Money.ZERO) }
    var creditSpent by remember { mutableStateOf(Money.ZERO) }
    var selectedFlag by remember { mutableStateOf(BankFlags.Flags.Wallet) }
    LaunchedEffect(Unit) {
        if (id != null) {
            runBlocking {
                val bank = App.UseCases.getBankRaw.execute(id)
                name = bank.name
                balance = bank.balance
            }
        }
    }
    val toggle = BottomSheet.Wrapper { t ->
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
                onValueChange = { balance = it.toDoubleOrNull() ?: Money.ZERO },
                label = "Saldo",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OTF(
                value = creditLimit.toString(),
                onValueChange = { creditLimit = it.toDoubleOrNull() ?: Money.ZERO },
                label = "Crédito Limit2",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OTF(
                value = creditSpent.toString(),
                onValueChange = { creditSpent = it.toDoubleOrNull() ?: Money.ZERO },
                label = "Crédito Gasto",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            EDM<BankFlags.Flags>(selectedFlag, { selectedFlag = it }, selectedFlag.title )

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
                                updated_at = null,
                                creditLimit = if (creditLimit == Money.ZERO) null else creditLimit,
                                creditSpent = if (creditSpent == Money.ZERO) null else creditSpent,
                                flag =selectedFlag.title
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
                                updated_at = App.Time.now(),
                                creditLimit = if (creditLimit == Money.ZERO) null else creditLimit,
                                creditSpent = if (creditSpent == Money.ZERO) null else creditSpent,
                                flag =selectedFlag.title
                            )
                        )
                    }
                    callback()
                    t()
                }
            }, text = "Salvar")
        }
    }
    return toggle
}