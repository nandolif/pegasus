package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.agenda.app.App
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.SingleDayVM
import kotlinx.coroutines.runBlocking

@Composable
fun SingleDay(day: Int, month: Int, year: Int) {
    val vm: SingleDayVM = SingleDayVM(day, month, year)
    val data by vm.events.collectAsState()
    Structure.Wrapper(header = { Structure.Header("$day/$month/$year") }) {
        LazyColumn {
            items(data) {
                Column {

                    TXT(it.description)
                    BTN(onClick = {
                        runBlocking {
                            App.UseCases.deleteEvent.execute(it.id!!)
                        }
                        Navigation.navController.navigate(Navigation.HomeRoute())
                    }, text = "Deletar")
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
        }, text = "Criar Evento nesta data")
    }

}
