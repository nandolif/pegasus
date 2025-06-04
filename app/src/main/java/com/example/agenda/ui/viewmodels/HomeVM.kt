package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agenda.app.App
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeVM() : ViewModel() {
    val data = MutableStateFlow<MutableMap<DayMonthYearObj, MonthYearObj>>(mutableMapOf())

    val eventCategories = MutableStateFlow(mutableListOf<EventCategory>())
    var title = MutableStateFlow("Eventos")

    fun getPage(date:DayMonthYearObj): MonthYearObj? {
        return data.value[date.copy(day = 1)]
    }

    suspend fun fetchPageData(date: DayMonthYearObj, reset: Boolean = false): MonthYearObj {
        if(reset) data.value = mutableMapOf()
        val data: MonthYearObj = App.UseCases.getWeekData.execute(date)
        return data
    }

    fun setTitle(currentPage: MonthYearObj) {
        title.value = if (currentPage.monthAndYear.year == App.Time.today.year) {
            Date.geMonthText(currentPage.monthAndYear)
        } else {
            "${Date.geMonthText(currentPage.monthAndYear)} - ${currentPage.monthAndYear.year}"
        }
    }


    private fun fetchInitialData() {
        viewModelScope.launch {
            data.value = App.UseCases.getWeeksData.execute(App.Time.today)

            eventCategories.value =
                App.Repositories.eventCategoryRepository.getAll().toMutableList()
        }

    }

    init {
        fetchInitialData()
    }
}