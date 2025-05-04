package com.example.agenda.ui.viewmodels

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import com.example.agenda.ui.component.Pager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class HomeVM() : ViewModel() {
    val data = App.UI.cache.getMonthYearList()
    val eventCategories = MutableStateFlow(mutableListOf<EventCategory>())
    var indexOffset = MutableStateFlow(0)


    init {
        runBlocking {
            eventCategories.value =
                App.Repositories.eventCategoryRepository.getAll().toMutableList()
        }
    }



}