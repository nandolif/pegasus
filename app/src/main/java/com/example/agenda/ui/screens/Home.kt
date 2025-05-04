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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.Pager
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.HomeVM
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

@Composable
fun Home() {
    val structureVM: StructureVM = viewModel()
    val viewModel: HomeVM = viewModel()
    val data by viewModel.data.collectAsState()
    val fetchMoreData = FetchMoreData()
    val indexOffset = viewModel.indexOffset
    val indexOffsetValue by indexOffset.collectAsState()
    val pagerState = rememberPagerState(pageCount = { Pager.pageCount(data.size) }, initialPage = 1)
    val currentPage: MonthYearObject = data[pagerState.currentPage]
    val days = currentPage.weeks.flatMap { it.days }
    App.UI.title = setTitle(currentPage)
    Column {
        Header(structureVM, pagerState, data,indexOffsetValue)
        Pager.Wrapper(data, currentPage) {
            Pager.Component(pagerState) {
                WeeksBox(days, currentPage.weeks.size) { day, cellHeight ->
                    DayItem(days, day, cellHeight)
                }
            }
            Pager.Effect(
                fetchMoreData = fetchMoreData,
                pagerState = pagerState,
                data = data,
                indexOffset = indexOffset
            )
        }
    }
}


private fun setTitle(currentPage: MonthYearObject): String {
    return if (currentPage.monthAndYear.year == App.Time.today.year) {
        Date.geMonthText(currentPage.monthAndYear)
    } else {
        "${Date.geMonthText(currentPage.monthAndYear)} - ${currentPage.monthAndYear.year}"
    }
}

class FetchMoreData : Pager.FetchMoreData<MonthYearObject> {
    override suspend fun execute(
        data: List<MonthYearObject>,
        pagerState: PagerState,
        last: Boolean,
        indexOffset: MutableStateFlow<Int>,
    ) {
        if (last) {
            val l = data.last()
            val date =
                Date.getDate(
                    DayMonthYearObj(
                        1,
                        l.monthAndYear.month + 1,
                        l.monthAndYear.year
                    )
                )
            App.UseCases.getWeeksDataInFuture.execute(listOf(date, true))
            return
        }
        val l = data.first()
        val date =
            Date.getDate(DayMonthYearObj(1, l.monthAndYear.month - 1, l.monthAndYear.year))
        App.UseCases.getWeeksDataInFuture.execute(listOf(date, false))
        pagerState.scrollToPage(1)
        indexOffset.value += 1
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
    val vm: HomeVM = viewModel()
    val eventCategories by vm.eventCategories.collectAsState()
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
                    Navigation.navController.navigate(
                        Navigation.SingleDayRoute(
                            day.date.day,
                            day.date.month,
                            day.date.year
                        )
                    )
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


            val horizontalPadding = if (isToday && day.date.day < 10) 4.dp else 2.dp
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
                    .padding(vertical = 2.dp, horizontal = horizontalPadding)
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
                        val eventCategory =
                            eventCategories.first { it.id == day.events[i].categoryId }
                        ItemContent(
                            day.events[i].description,
                            highlight = true,
                            backgroundColor = Color(eventCategory.backgroundColor.toULong()),
                            textColor = Color(eventCategory.textColor.toULong())
                        )
                    }
                } else {
                    for (i in 0..<day.events.size) {
                        val eventCategory =
                            eventCategories.first { it.id == day.events[i].categoryId }
                        ItemContent(
                            day.events[i].description,
                            highlight = true,
                            backgroundColor = Color(eventCategory.backgroundColor.toULong()),
                            textColor = Color(eventCategory.textColor.toULong())
                        )
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
        if (it.goalId == null) {
            it.amount.toDouble()
        } else {
            -it.amount.toDouble()

        }
    }
    ItemContent(Money.format(totalAmount.toFloat()), totalAmount > 0)
}

@Composable
private fun ItemContent(
    text: String,
    highlight: Boolean = false,
    backgroundColor: Color = Theme.Colors.D.color,
    textColor: Color = Theme.Colors.A.color,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                if (highlight) backgroundColor else textColor,
                RoundedCornerShape(4.dp)
            )
            .fillMaxWidth()
            .height(12.dp)


    ) {
        TXT(
            s = text,
            maxLines = 1,
            fs = 8,
            color = if (highlight) textColor else backgroundColor
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
}