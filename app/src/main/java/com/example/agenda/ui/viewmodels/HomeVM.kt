package com.example.agenda.ui.viewmodels

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.EventCategoryEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.MonthYearObject
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import com.example.agenda.ui.component.Pager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class HomeVM() : ViewModel() {
    val data = App.UI.cache.getMonthYearList()
    val eventCategories = MutableStateFlow(mutableListOf<EventCategoryEntity>())
    var indexOffset = MutableStateFlow(0)


    init {
        runBlocking {
            eventCategories.value =
                App.Repositories.eventCategoryRepository.getAll().toMutableList()
        }
    }



}