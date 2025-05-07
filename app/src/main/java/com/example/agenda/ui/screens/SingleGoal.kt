package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.SingleGoalVM
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.runBlocking

@Composable
fun SingleGoal(id: String) {
    val vm = SingleGoalVM(id)
    val structureVM: StructureVM = viewModel()
    val goal by vm.goal.collectAsState()
    val transactions by vm.transactions.collectAsState()

    Column {
        Header(structureVM)
        if (goal == null) {
            TXT("Carregando...")
        } else {
            Column {
                TXT(goal!!.title)
                TXT(Money.resolve(goal!!.amount).text)
                TXT(Money.resolve(goal!!.actualAmount!!).text)
                BTN(onClick = {
                    runBlocking {
                        App.UseCases.deleteGoal.execute(goal!!)
                    }
                    Navigation.navController.navigate(Navigation.GoalsRoute())
                }, text="Deletar")
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(transactions) {
                    //Item(it)
                    Spacer(Modifier.height(16.dp))
                }

            }
        }
    }
}