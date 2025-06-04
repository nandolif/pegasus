package com.example.agenda.ui.component.transactions.createBottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.component.TXT

@Composable
fun BankCard(bank: Bank, callback: () -> Unit) {
    Column(modifier = Modifier.clickable { callback() }.fillMaxWidth()) {
        TXT(bank.name)
        TXT(bank.balance.toString())
        TXT(bank.creditSpent.toString())
        TXT(bank.creditLimit.toString())
    }
}