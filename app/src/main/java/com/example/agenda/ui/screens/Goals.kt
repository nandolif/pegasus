package com.example.agenda.ui.screens

import Money
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.GoalsVM
import com.example.agenda.ui.viewmodels.StructureVM

@Composable
fun Goals() {
    val vm: GoalsVM = viewModel()
    val structureVM: StructureVM = viewModel()
    val goals by vm.goals.collectAsState()
    App.UI.title = "Metas"
    Column {
        Header(structureVM)
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(goals) { goal ->
                    Box(
                        modifier = Modifier
                            .background(Theme.Colors.B.color)
                            .padding(4.dp)
                            .clickable {
                                Navigation.navController.navigate(Navigation.SingleGoalRoute(goal.id!!))
                            }) {

                        TXT(
                            "${goal.title}: ${Money.format(goal.amount)} [Alcançado: ${
                                Money.format(
                                    goal.actualAmount!!,
                                )
                            }]"
                        )
                    }
                }
            }
        }
    }
}