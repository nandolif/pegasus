package com.example.agenda.ui.component.transactions.createBottomSheet.parcel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.agenda.app.helpers.Parcel
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.Form
import com.example.agenda.ui.component.Modal

@Composable
fun SelectParcelRecurrence(parcel: MutableState<Parcel.Data>) {
    val parcelRecurrenceModal =
        Modal.Wrapper("Parcelamento") { toggle ->
            Modal.List<Unit> { _, _ ->
                Modal.Item(
                    callback = {
                        parcel.value =
                            parcel.value.copy(recurrence = Parcel.Recurrence.Monthly);toggle()
                    },
                    text = Parcel.Recurrence.Monthly.type,
                    isActive = parcel.value.recurrence == Parcel.Recurrence.Monthly,

                    )
                Modal.Item(
                    callback = {
                        parcel.value =
                            parcel.value.copy(recurrence = Parcel.Recurrence.SemiMonthly);toggle()
                    },
                    text = Parcel.Recurrence.SemiMonthly.type,
                    isActive = parcel.value.recurrence == Parcel.Recurrence.SemiMonthly,
                )
                Modal.Item(
                    callback = {
                        parcel.value =
                            parcel.value.copy(recurrence = Parcel.Recurrence.BiMonthly);toggle()
                    },
                    text = Parcel.Recurrence.BiMonthly.type,
                    isActive = parcel.value.recurrence == Parcel.Recurrence.BiMonthly
                )
                Modal.Item(
                    callback = {
                        parcel.value =
                            parcel.value.copy(recurrence = Parcel.Recurrence.TriMonthly);toggle()
                    },
                    text = Parcel.Recurrence.TriMonthly.type,
                    isActive = parcel.value.recurrence == Parcel.Recurrence.TriMonthly
                )
                Modal.Item(
                    callback = {
                        parcel.value =
                            parcel.value.copy(recurrence = Parcel.Recurrence.QadriMonthly);toggle()
                    },
                    text = Parcel.Recurrence.QadriMonthly.type,
                    isActive = parcel.value.recurrence == Parcel.Recurrence.QadriMonthly
                )
            }

        }


    Form.Row(
        icon = Theme.Icons.Event.icon,
        callback = { parcelRecurrenceModal() },
        placeholder = parcel.value.recurrence.type,
        text = parcel.value.recurrence.type
    )

}