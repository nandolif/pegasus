package com.example.agenda.ui.component.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agenda.app.helps.Calculator
import com.example.agenda.domain.entities.Goal
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT

@Composable
fun GoalCard(goal: Goal, callback: () -> Unit) {
    val percentage = Calculator.percentByTotal(goal.actualAmount ?: 0.0, goal.amount)

    Column(
        Modifier
            .background(Theme.Colors.B.color)
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { callback() }
        ,verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TXT(goal.title)
            TXT(percentage.text)
        }
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TXT(Money.resolve(goal.actualAmount ?: 0.0).text)
                TXT(Money.resolve(goal.amount).text)
            }
            Box {
                HorizontalDivider(
                    thickness = 4.dp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Theme.Colors.C.color
                )
                HorizontalDivider(
                    thickness = 4.dp,
                    modifier = Modifier.fillMaxWidth(fraction = (percentage.value / 100).toFloat()),
                    color = Theme.Colors.D.color
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewGoalCard() {
    GoalCard(
        goal = Goal(
            id = null,
            created_at = null,
            updated_at = null,
            achieved = false,
            amount = 1000.0,
            title = "Guarde 1000 reais",
            actualAmount = 500.0
        ),
        callback = { }
    )
}