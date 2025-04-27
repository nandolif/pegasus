package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.agenda.app.App
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.SingleDayVM
import kotlinx.coroutines.runBlocking

@Composable
fun SingleDay(day: Int, month: Int, year: Int) {
    val vm: SingleDayVM = SingleDayVM(day, month, year)
    val data by vm.events.collectAsState()

    Column {

        LazyColumn {
            items(data) {
                Column {

                    TXT(it.description)
                    BTN(onClick = {
                        runBlocking {
                            App.UseCases.deleteEvent.execute(it.id!!)
                        }
                        Navigation.navController.navigate(Navigation.HomeRoute())
                    }) { TXT(s = "Deletar", color = Theme.Colors.A.color) }
                }
            }
        }
        BTN(onClick = {
            Navigation.navController.navigate(
                Navigation.CreateEventRoute(
                    Date.dayMonthYearToString(
                        DayMonthYearObj(day, month, year)
                    )
                )
            )
        }) {
            TXT(s= "Criar Evento nesta data", color = Theme.Colors.A.color)
        }
    }

}
