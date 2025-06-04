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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.TransactionWithData
import com.example.agenda.ui.BankFlags
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.screens.Banks
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.screens.Transactions

@Composable
fun TransactionCard(data: TransactionWithData,callback: () -> Unit) {
    val flag = BankFlags.get(data.bank.flag)

    Column(
        modifier = Modifier.clickable { callback() }.background(Theme.Colors.A.color, shape = RoundedCornerShape(6.dp)).padding(4.dp).height(100.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
      ) {
          Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
              Box(
                  Modifier
                      .background(data.transaction.type.color, CircleShape)
                      .size(17.dp),
                  contentAlignment = Alignment.Center
              ) {
                 Icon(data.transaction.type.icon, contentDescription = data.transaction.type.value)
              }
              Box(
                  Modifier
                      .background(Theme.Colors.D.color, CircleShape)
                      .size(17.dp),
                  contentAlignment = Alignment.Center
              ) {
                  Image(
                      painter = painterResource(flag.painter),
                      contentDescription = flag.title,
                      modifier = Modifier.size(17.dp)
                  )
              }

              Box(
                  Modifier
                      .background(Color(data.category.backgroundColor.toULong()), CircleShape)
                      .size(17.dp),
                  contentAlignment = Alignment.Center
              ) {
                  Icon(Theme.Icons.TransactionCategory.icon, contentDescription = data.category.name)
              }

          }

      }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TXT(data.transaction.description, fs = 10)
        }
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TXT(Money.resolve(data.transaction.amount, withCurrency = true, withSign = false).text, fontWeight = FontWeight.Bold)
            Row {
                TXT(Date.dayMonthYearToString(DayMonthYearObj(data.transaction.day,data.transaction.month,data.transaction.year)), fs = 10)
            }
        }
    }
}


@Preview
@Composable
fun TransactionCardPreview() {
    TransactionCard(
        TransactionWithData(
            transaction = Transaction(
                id = null,
                day = 1,
                month = 12,
                year = 2001,
                amount = 1000.0,
                description = "Mercado",
                created_at = null,
                updated_at = null,
                bankId = Banks.Default.NAME_AND_ID,
                recurrenceId = null,
                ghost = false,
                goalId = null,
                canceled = false,
                canceledDay = null,
                canceledMonth = null,
                canceledYear = null,
                nDays = null,
                nWeeks = null,
                nMonths = null,
                nYears = null,
                recurrenceType = null,
                categoryId = TransactionCategories.Default.Transport.NAME_AND_ID,
                personId = null,
                isCredit = false,
                type = Transactions.Type.EXPENSE
            ),
            bank = Bank(
                name = Banks.Default.NAME_AND_ID,
                balance = 100.0,
                id = null,
                created_at = null,
                updated_at = null,
                creditLimit = 600.0,
                creditSpent = 0.0,
                flag = BankFlags.Flags.Alelo.title
            ),
            category = TransactionCategory(
                id = null,
                created_at = null,
                updated_at = null,
                name = "Mercado",
                textColor = Theme.Colors.A.color.toString(),
                backgroundColor = Theme.Colors.D.color.toString(),
                type = Transactions.Type.EXPENSE
            )
        )
        ,{}
    )
}