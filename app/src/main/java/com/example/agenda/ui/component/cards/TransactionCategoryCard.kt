package com.example.agenda.ui.component.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.system.Navigation

@Composable
fun TransactionCategoryCard(data: TransactionCategory) {
    Row(
        modifier = Modifier
            .background(Color(data.backgroundColor.toULong()), RoundedCornerShape(6.dp))
            .padding(vertical = 6.dp, horizontal = 12.dp)
            .clickable {
                Navigation.navController.navigate(
                    TransactionCategories.Screens.SingleTransactionCategory.Route(
                        data.id!!
                    )
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Icon(Theme.Icons.TransactionCategory.icon, contentDescription = data.name, tint = Color(data.textColor.toULong()))
            }
            TXT(
                data.name,
                color = Color(data.textColor.toULong()),
                fs = 12,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TransactionCategoryGrid(title: String, data: List<TransactionCategory>) {

    Column(
        modifier = Modifier.padding(horizontal = 10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Theme.Colors.C.color, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                )
                .padding(8.dp),

            ) {
            TXT(title)
            TXT(data.count().toString())
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .background(Theme.Colors.B.color, RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            items(data) {
                TransactionCategoryCard(it)
            }
        }
    }
}