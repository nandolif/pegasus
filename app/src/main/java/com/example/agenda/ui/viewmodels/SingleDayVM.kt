package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class SingleDayVM(day: Int, month: Int, year: Int) : ViewModel() {
    val events: MutableStateFlow<List<Event>> = MutableStateFlow(listOf())

    init {
        runBlocking {
            events.value = App.UseCases.getEventsByDate.execute(DayMonthYearObj(day, month, year))
        }
    }

}