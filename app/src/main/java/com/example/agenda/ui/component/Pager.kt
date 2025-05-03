package com.example.agenda.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun Test(){
    Pager.Component()
}


object Pager {

    @Composable
    fun Component(){
        val data = listOf(8,0,1)
        val realData = listOf(1,2,3,4,5,6,7,8,9)
        val pagerState = rememberPagerState(
            initialPage = 500,
            pageCount = { 1000 }
        )
        var lastPage = pagerState.currentPage


        HorizontalPager(pagerState) {
            Column(Modifier.fillMaxSize()) {

                val page = realData[data[it % 2]]
                Text(text = page.toString())
            }
        }


        LaunchedEffect(pagerState.currentPage){

            if(pagerState.currentPage > lastPage){

            }


            lastPage = pagerState.currentPage
        }
    }
}