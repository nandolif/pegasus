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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.objects.DateObj
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.MonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.Pager
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.component.form.CreateEventForm
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.HomeVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun Home() {
    val viewModel: HomeVM = viewModel()
    val data by viewModel.data.collectAsState()
    val currentDate = remember { mutableStateOf(App.Time.today.copy(day = 1)) }
    val currentPage = viewModel.getPage(currentDate.value)
    val selectDate = remember { mutableStateOf(App.Time.today) }
    val coroutineScope = rememberCoroutineScope()

    val toggleCreateEventForm =
        CreateEventForm(
            selectDate,
            callback = {
                coroutineScope.launch {
                    viewModel.fetchPageData(
                        currentDate.value,
                        true
                    )
                }
            })
    val title: String by viewModel.title.collectAsState()

    fun toggleForm(date: DayMonthYearObj = App.Time.today) {
        selectDate.value = date
        toggleCreateEventForm()
    }
    if (currentPage != null) {
        viewModel.setTitle(currentPage)
    }
    val state = rememberPagerState(
        initialPage = 200,
        pageCount = { 400 }
    )
    Structure.Wrapper(
        bottom = { Structure.BottomBar({ toggleForm() }) },
        header = {
            Structure.Header(title, actions = {

                IconButton(onClick = {
                    Navigation.navController.navigate(EventCategories.Screens.AllEventCategories.Route())
                }) {
                    Icon(
                        Theme.Icons.EventCategory.icon,
                        contentDescription = "Categoria de Eventos"
                    )
                }
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = Theme.Colors.C.color,
                            modifier = Modifier.offset((-6).dp, 4.dp)
                        ) { Text(App.Time.today.day.toString()) }
                    }) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            currentDate.value = App.Time.today.copy(day = 1)
                        }
                    }) {
                        Icon(Theme.Icons.Event.icon, contentDescription = "Calendário")
                    }
                }

            })
        }) {
        Pager.Component(
            currentDate = currentDate,
            state = state,
            data = data,
            callback = {  viewModel.fetchPageData(currentDate.value)  },
        ) {
            if (currentPage != null) {
                val days = currentPage.weeks.flatMap { it.days }

                WeeksBox(days, currentPage.weeks.size) { day, cellHeight ->
                    DayItem(days, day, cellHeight, ::toggleForm)
                }
            }
        }
    }
}


@Composable
private fun WeeksBox(
    days: List<DateObj>,
    rows: Int,
    content: @Composable (day: DateObj, cellHeight: Dp) -> Unit,
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
private fun DayItem(
    days: List<DateObj>,
    day: DateObj,
    cellHeight: Dp,
    toggleForm: (date: DayMonthYearObj) -> Unit,
) {
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
                    toggleForm(DayMonthYearObj(day.date.day, day.date.month, day.date.year))
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
private fun TransactionItem(transactions: List<Transaction>) {
    val totalAmount = transactions.sumOf {
        if (it.goalId == null) {
            it.amount.toDouble()
        } else {
            -it.amount.toDouble()

        }
    }
    ItemContent(Money.resolve(totalAmount).text, totalAmount > 0)
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