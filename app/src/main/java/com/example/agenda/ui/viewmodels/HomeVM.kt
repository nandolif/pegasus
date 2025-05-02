package com.example.agenda.ui.viewmodels

import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.EventCategoryEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.MonthYearObject
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class HomeVM() : ViewModel() {
    val data = App.UI.cache.getMonthYearList()
    val eventCategories = MutableStateFlow(mutableListOf<EventCategoryEntity>())


    init {
        runBlocking {
            eventCategories.value = App.Repositories.eventCategoryRepository.getAll().toMutableList()
        }
    }
    var indexOffset = MutableStateFlow(0)
    suspend fun getMoreData(d: List<MonthYearObject>, pagerState: PagerState) {
        when (pagerState.currentPage) {
            d.lastIndex -> {
                val l = d.last()
                val date =
                    Date.getDate(DayMonthYearObj(1, l.monthAndYear.month + 1, l.monthAndYear.year))
                App.UseCases.getWeeksDataInFuture.execute(listOf(date, true))
            }

            0 -> {
                val l = d.first()
                val date =
                    Date.getDate(DayMonthYearObj(1, l.monthAndYear.month - 1, l.monthAndYear.year))
                App.UseCases.getWeeksDataInFuture.execute(listOf(date, false))
                pagerState.scrollToPage(1)
                indexOffset.value = 1
            }
        }
    }
}