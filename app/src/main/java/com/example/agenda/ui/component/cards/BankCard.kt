package com.example.agenda.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agenda.app.helps.Calculator
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.BankFlags
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT

@Composable
fun BankCard(data: Bank, callback: () -> Unit) {
    val flag = BankFlags.get(data.flag)
    val textColor = if (flag.isLight) Theme.Colors.A.color else Theme.Colors.D.color
    var showBalance by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clickable { callback() }
            .background(Theme.Colors.B.color, RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .background(flag.color, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .padding(8.dp)

        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    Modifier
                        .background(Theme.Colors.D.color, CircleShape)
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(flag.painter),
                        contentDescription = flag.title,
                        modifier = Modifier.size(30.dp)
                    )
                }
                TXT(flag.title, color = textColor, fontWeight = FontWeight.Bold)
            }
            IconButton(
                onClick = {showBalance = !showBalance},
            ) {
                Icon(Icons.Default.RemoveRedEye, contentDescription = "Show Balance", tint = textColor)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val balance = if(showBalance) Money.resolve(data.balance, withSign = true, withCurrency = true).text else "*************"
            TXT(balance, fontWeight = FontWeight.Black, fs = 20)
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)

            ,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (data.creditLimit != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TXT(
                        "Crédito Gasto: ${Money.resolve(data.creditSpent ?: 0.0).text}",
                        fontWeight = FontWeight.Bold
                    )
                    TXT(
                        "Limite: ${Money.resolve(data.creditLimit).text}",
                        fontWeight = FontWeight.Bold
                    )
                }
                Icons.Default.ArrowDropDown
                val width =
                    Calculator.percentByTotal(data.creditSpent ?: 0.0, data.creditLimit).value / 100
                if (width > 0) {

                    HorizontalDivider(
                        thickness = 4.dp,
                        color = flag.color,
                        modifier = Modifier.fillMaxWidth(fraction = width.toFloat())
                    )
                }
            } else TXT("Somente Débito", fontWeight = FontWeight.Bold)

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBankCard() {
    BankCard(
        data = Bank(
            name = "Carteira",
            balance = 200.0,
            id = null,
            created_at = null,
            updated_at = null,
            creditLimit = 600.0,
            creditSpent = 400.0,
            flag = BankFlags.Flags.Nubank.title
        )
    ) { }
}