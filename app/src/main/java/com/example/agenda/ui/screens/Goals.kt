package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.cards.GoalCard
import com.example.agenda.ui.component.form.CreateGoalForm
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.GoalsVM

@Composable
fun Goals() {
    val vm: GoalsVM = viewModel()
    val goals by vm.goals.collectAsState()
    val toggleCreateGoalForm = CreateGoalForm(callback = {vm.fetchData()})
    Structure.Wrapper(header = { Structure.Header("Metas")}, bottom = { Structure.BottomBar(toggleCreateGoalForm)}) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(horizontal = 8.dp),

        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(goals) { goal ->
                    GoalCard(goal) {
                        Navigation.navController.navigate(Navigation.SingleGoalRoute(goal.id!!))
                    }
                }
            }
        }
    }
}