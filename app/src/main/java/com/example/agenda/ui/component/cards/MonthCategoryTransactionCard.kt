package com.example.agenda.ui.component.cards

import Money
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agenda.app.helps.Calculator
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.domain.objects.TransactionWithData
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.screens.Transactions

private data class  MonthCategoryTransactionData(
    val data: TransactionCategory,
    val percentage: Money.Currency,
    val total: Double,
)

@Composable
fun MonthCategoryTransactionList(
    total: Double,
    type: Transactions.Type,
    data: List<TransactionWithData>,
) {
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TXT(type.value)
                TXT("Total: " + Money.resolve(total, withCurrency = true, withSign = false).text)
            }
        }
        Column(Modifier.background(Theme.Colors.A.color, RoundedCornerShape(6.dp))) {
            if (data.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    TXT("Nenhuma ${type.value} Registrada Neste Mês")
                }
            } else {
                val monthCategoryTransactionDataList = mutableListOf<MonthCategoryTransactionData>()
                val transactionCategories = mutableListOf<TransactionCategory>()

                for (transaction in data) {
                    if (!transactionCategories.contains(transaction.category)) {
                        transactionCategories.add(transaction.category)
                    }
                }

                val transactionTotal = data.fold(Money.ZERO) { acc, i -> acc + i.transaction.amount }

                for (category in transactionCategories) {
                    val transactionWithSameCategory = data.filter { it.category == category }
                    val t = transactionWithSameCategory.fold(Money.ZERO) { acc, i -> acc + i.transaction.amount }
                    val percentage = Calculator.percentByTotal(t, transactionTotal)
                    monthCategoryTransactionDataList.add(MonthCategoryTransactionData(category, percentage, t))
                }


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(monthCategoryTransactionDataList.sortedByDescending { it.percentage.value }) {
                        MonthCategoryTransactionCard(
                            total = it.total,
                            data = it.data,
                            percentageValue = it.percentage
                        ) { }
                    }
                }
            }
        }
    }
}

@Composable
fun MonthCategoryTransactionCard(
    percentageValue: Money.Currency,
    total: Double,
    data: TransactionCategory,
    callback: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { callback() },
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TXT(data.name, color = Color(data.backgroundColor.toULong()), fontWeight = FontWeight.SemiBold)
            TXT(Money.resolve(total, withCurrency = true, withSign = false).text)
        }
        Box {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.Colors.B.color, RoundedCornerShape(6.dp))
                    .height(14.dp),
            )
            val percentage = (percentageValue.value / 100).toFloat()
            val rounded = if(percentage > .99) RoundedCornerShape(6.dp) else RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = percentage)
                    .background(Color(data.backgroundColor.toULong()), rounded)
                    .height(14.dp),
            )
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TXT(percentageValue.text, fs = 12, color = Color(data.textColor.toULong()))

            }
        }
    }
}
