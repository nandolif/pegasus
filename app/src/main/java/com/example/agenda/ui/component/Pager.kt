package com.example.agenda.ui.component

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.objects.DayMonthYearObj
import kotlinx.coroutines.flow.MutableStateFlow

object Pager {
    @Composable
    fun <T> Component(
        state: PagerState = rememberPagerState(
            initialPage = 200,
            pageCount = { 400 }
        ),
        currentDate: MutableState<DayMonthYearObj>,
        data: MutableMap<DayMonthYearObj, T>,
        callback: suspend () -> T,
        previousPageCallback: () -> Unit = {
            currentDate.value = DayMonthYearObj(
                currentDate.value.day,
                currentDate.value.month - 1,
                currentDate.value.year
            )
        },
        nextPageCallback: () -> Unit = {
            currentDate.value = DayMonthYearObj(
                currentDate.value.day,
                currentDate.value.month + 1,
                currentDate.value.year
            )
        },
        content: @Composable () -> Unit,
    ) {
        LaunchedEffect(data) {
            if (data.isEmpty()) data[currentDate.value] = callback()
        }
        var previousPage by remember { mutableStateOf(state.currentPage) }

        HorizontalPager(state = state) {
            content()
        }

        LaunchedEffect(state.currentPage) {
            if (state.currentPage == previousPage) return@LaunchedEffect
            if (state.currentPage < previousPage) previousPageCallback()
            if (state.currentPage > previousPage) nextPageCallback()
            currentDate.value = Date.getDate(currentDate.value)
            if (data[currentDate.value] == null) data[currentDate.value] = callback()
            previousPage = state.currentPage
        }
    }
}