package com.example.agenda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.DateObject
import com.example.agenda.app.objects.MonthYearObject
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.HomeVM
import com.example.agenda.ui.viewmodels.StructureVM

@Composable
fun Home() {
    val viewModel: HomeVM = viewModel()
    val structureVM: StructureVM = viewModel()
    val data by viewModel.data.collectAsState()
    val pagerState = rememberPagerState(pageCount = { data.size * 400 }, initialPage = 1)
    val currentPage: MonthYearObject? = data.getOrNull(pagerState.currentPage)
    if (currentPage != null) {
        val days = currentPage.weeks.flatMap { it.days }
        App.UI.title = if (currentPage.monthAndYear.year == App.Time.today.year) {
            Date.geMonthText(currentPage.monthAndYear)
        } else {
            "${Date.geMonthText(currentPage.monthAndYear)} - ${currentPage.monthAndYear.year}"
        }
        Column {
            Header(structureVM, pagerState, data)
            Pager(pagerState) {
                WeeksBox(days, currentPage.weeks.size) { day, cellHeight ->
                    DayItem(days, day, cellHeight)
                }
            }
            Effect(pagerState, data, viewModel)

        }
    } else {
        Loading()
    }
}


@Composable
private fun Effect(pagerState: PagerState, data: List<MonthYearObject>, viewModel: HomeVM) {
    LaunchedEffect(pagerState.currentPage) {
        App.UI.currentUIDate = data[pagerState.currentPage].monthAndYear
        viewModel.getMoreData(data, pagerState)
    }
}

@Composable
private fun Pager(
    pagerState: PagerState,
    content: @Composable () -> Unit,
) {
    HorizontalPager(state = pagerState, key = { index -> index }) {
        content()
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No data available")
    }
}

@Composable
private fun WeeksBox(
    days: List<DateObject>,
    rows: Int,
    content: @Composable (day: DateObject, cellHeight: Dp) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        val totalSpacing = (rows - 1) * 4.dp
        val cellHeight = (maxHeight - totalSpacing) / rows

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 2.dp)
        ) {
            itemsIndexed(
                items = days,
                key = { index, _ -> index }
            ) { _, day ->
                content(day, cellHeight)
            }
        }

    }
}

@Composable
private fun DayItem(days: List<DateObject>, day: DateObject, cellHeight: Dp) {
    val interactionSource = remember { MutableInteractionSource() }
    val isWeekend = (days.indexOf(day) + 1) % 7 == 0 || (days.indexOf(day) + 2) % 7 == 0
    val isToday =
        App.Time.today.day == day.date.day && App.Time.today.month == day.date.month && App.Time.today.year == day.date.year
    Box(
        modifier = Modifier
            .height(cellHeight)
            .fillMaxWidth()
            .background(
                if (isWeekend) Theme.Colors.C.color else Theme.Colors.B.color
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = true,
                    color = Theme.Colors.D.color
                )
            ) {

                if (day.events.isNotEmpty()) {
                    Navigation.navController.navigate(Navigation.SingleDayRoute(day.date.day, day.date.month, day.date.year))
                } else {
                    Navigation.navController.navigate(
                        Navigation.CreateEventRoute(
                            date = Date.dayMonthYearToString(day.date)
                        )
                    )
                }
            }
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
        ) {


            Text(
                text = day.date.day.toString(),
                color = if (isToday) Theme.Colors.A.color else Theme.Colors.D.color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = if (isToday) Modifier
                    .background(
                        Theme.Colors.D.color,
                        CircleShape
                    )
                    .padding(2.dp)
                else Modifier.padding(2.dp)

            )


            Spacer(modifier = Modifier.height(2.dp))

            val availableSpace =
                if (day.transactions.isNotEmpty()) 6 else 7
            val totalSize =
                if (day.transactions.isNotEmpty()) 1 + day.events.size else day.events.size
            if (day.transactions.isNotEmpty()) {

                TransactionItem(day.transactions)
            }
            if (day.events.isNotEmpty()) {
                if (day.events.size > availableSpace) {

                    for (i in 0..<availableSpace) {
                        ItemContent(day.events[i].description)
                    }
                } else {
                    for (i in 0..<day.events.size) {
                        ItemContent(day.events[i].description)
                    }
                }

            }
            if (totalSize > availableSpace) {
                Text(
                    text = "...",
                    color = Theme.Colors.D.color,
                    modifier = Modifier.offset(y = (-7).dp)
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(transactions: List<TransactionEntity>) {
    val totalAmount = transactions.sumOf {
        if(it.goalId == null) {

            it.amount.toDouble()
        } else {
            -it.amount.toDouble()

        }
    }
    ItemContent(totalAmount.toString(), totalAmount > 0)
}

@Composable
private fun ItemContent(text: String, highlight: Boolean = false) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                if (!highlight) Theme.Colors.A.color else Theme.Colors.D.color,
                RoundedCornerShape(4.dp)
            )
            .fillMaxWidth()
            .height(12.dp)


    ) {
        TXT(
            s = text,
            maxLines = 1,
            fs = 8,
            color = if (!highlight) Theme.Colors.D.color else Theme.Colors.A.color
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
}