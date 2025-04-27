package com.example.agenda.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.app.App
import com.example.agenda.app.common.EventType
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.DateDialog
import com.example.agenda.ui.component.EDM
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.runBlocking

@Composable
fun CreateEvent(eventDate: String?) {

    var description by remember { mutableStateOf("") }
    var isOpen by remember { mutableStateOf(false) }
    var date by remember {
        mutableStateOf(
            if (eventDate != null) {
                Date.stringToDayMonthYear(eventDate)
            } else {
                null
            }
        )
    }

    fun onAccept(accept: Long) {
        Date.longToDayMonthYear(accept).also {
            date = DayMonthYearObj(day = it.day, month = it.month, year = it.year)
            isOpen = false
        }
    }

    fun onDismiss() {
        isOpen = !isOpen
    }
    DateDialog(
        isDialogVisible = isOpen,
        onAccept = { onAccept(it) },
        onDismiss = { onDismiss() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TXT("Criar Evento", 32)

        Spacer(modifier = Modifier.height(8.dp))

        OTF(
            value = description,
            onValueChange = { description = it },
            label = "Descrição",
        )

        Spacer(modifier = Modifier.height(8.dp))
        var select by remember { mutableStateOf(EventType.REMINDER) }
        EDM<EventType>(
            selected = select,
            onSelectedChange = { select = it },
            label = "Escolha uma opção",
        )

        Spacer(modifier = Modifier.height(8.dp))

        var select2 by remember { mutableStateOf<RECURRENCE?>(null) }

        Row(Modifier.fillMaxWidth()) {
            EDM<RECURRENCE>(
                selected = select2,
                onSelectedChange = { select2 = it },
                label = "Recorrência",
            )

            if (select2 != null) {
                Box(
                    modifier = Modifier
                        .clickable {
                            select2 = null
                        }
                        .padding(4.dp)
                        .border(2.dp, Theme.Colors.D.color, shape = RoundedCornerShape(4.dp))
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Theme.Colors.D.color
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        var quantity by remember { mutableIntStateOf(7) }

        if (select2 != null) {
            OTF(
                ph = "Quantidade",
                label = "Quantidade",
                value = quantity.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) quantity = it.toInt() else quantity = 0
                },
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .clickable {
                    isOpen = !isOpen
                }
                .fillMaxWidth()) {

            OTF(
                value = if (date != null) Date.dayMonthYearToString(date!!) else "",
                onValueChange = { date = Date.longToDayMonthYear(it.toLong()) },
                label = "Data",
                ro = true,
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        BTN(onClick = {
            if (date == null) return@BTN
            if (description.isEmpty()) return@BTN
            if (select2 != null && quantity == 0) return@BTN

            runBlocking {
                App.UseCases.createEvent.execute(
                    input = Event(
                        day = date!!.day,
                        month = date!!.month,
                        year = date!!.year,
                        description = description,
                        id = null,
                        created_at = null,
                        updated_at = null,
                        recurrenceType = select2,
                        nDays = if (select2 == RECURRENCE.EVERY_N_DAYS) quantity else null,
                        nWeeks = if (select2 == RECURRENCE.EVERY_N_WEAK) quantity else null,
                        nMonths = if (select2 == RECURRENCE.EVERY_N_MONTH_LAST_DAY) quantity else null,
                        nYears = if (select2 == RECURRENCE.EVERY_N_YEARS) quantity else null,
                        recurrenceId = null,
                        eventType = select
                    )
                )
            }


//            date = null
//            description = ""
//            select = EventType.REMINDER
//            select2 = null
//            quantity = 7
            Navigation.navController.navigate(Navigation.HomeRoute())
        }) { TXT(s = "Salvar", color = Theme.Colors.A.color) }
    }
}