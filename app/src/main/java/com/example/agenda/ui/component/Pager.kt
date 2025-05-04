package com.example.agenda.ui.component

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow

object Pager {
    fun pageCount(size: Int): Int {
        return size * 200
    }

    interface FetchMoreData<T> {
        suspend fun execute(
            data: List<T>,
            pagerState: PagerState,
            last: Boolean = false,
            indexOffset: MutableStateFlow<Int>,
        )
    }

    @Composable
    fun <T> Wrapper(
        data: List<T>,
        currentPage: T?,
        noData: @Composable () -> Unit = {},
        content: @Composable (currentPage: T) -> Unit = {},
    ) {

        if (data.isNotEmpty()) {
            if (currentPage != null) {
                content(currentPage)
            } else {
                Loading()
            }
        } else {
            noData()
        }

    }

    @Composable
    fun Loading() {
        BTN("Carregando", {})
    }

    @Composable
    fun Component(pagerState: PagerState, content: @Composable () -> Unit = {}) {
        HorizontalPager(state = pagerState, key = { index -> index }) {
            content()
        }
    }

    @Composable
    fun <T> Effect(
        fetchMoreData: FetchMoreData<T>,
        pagerState: PagerState,
        data: List<T>,
        indexOffset: MutableStateFlow<Int>,
    ) {
        LaunchedEffect(pagerState.currentPage) {
            when (pagerState.currentPage) {
                data.lastIndex -> {
                    fetchMoreData.execute(data, pagerState, true, indexOffset)
                }

                0 -> {
                    fetchMoreData.execute(data, pagerState, false, indexOffset)
                }
            }
        }
    }
}