package com.example.agenda.ui.component.transactions.createBottomSheet.parcel

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.example.agenda.app.helpers.Parcel
import com.example.agenda.domain.entities.Bank
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.Form
import com.example.agenda.ui.component.Modal

@Composable
fun SelectParcelQuantity(
    bank: Bank,
    currency: Money.Currency,
    parcel: MutableState<Parcel.Data>,
    feesInput: MutableState<TextFieldValue>,
    items: List<Int>,
) {
    fun getFeesInputPlaceholder(): String {
        if (parcel.value.fees.value == Money.ZERO) return "Juros"
        return parcel.value.fees.text
    }

    fun getFeesInputValue(): TextFieldValue {
        if (parcel.value.fees.value == Money.ZERO) {
            return feesInput.value.copy(
                text = "",
            )
        }
        return feesInput.value
    }

    val toggleParcelModal = Modal.Wrapper("Parcelas") { toggle ->
        Modal.List(
            items = items,
            extraList = {
                Modal.Item(
                    callback = { toggle() },
                    text = "Outras Parcelas",
                    isActive = false,
                )
            }
        ) { index, item ->
            val itemTotal = Money.resolve(
                Parcel.total(
                    currency.value,
                    parcel.value.fees.value,
                    item
                )
            )
            val itemParcel =
                Money.resolve(Parcel.amount(itemTotal.value, item))
            Modal.Item(
                isActive = parcel.value.quantity == item,
                callback = {
                    parcel.value = parcel.value.copy(
                        quantity = item,
                        total = itemTotal
                    )
                    toggle()
                },
                text = "${item}X de ${itemParcel.text}",
                extraInfo = itemTotal.text
            )
        }
    }
        Row {
            val text =
                if (items.isEmpty()) "Nenhuma opção disponível" else "Parcelar em ${parcel.value.quantity}X"
            Form.Row(
                icon = Theme.Icons.Parcel.icon,
                callback = toggleParcelModal,
                placeholder = text,
                text = text,
                extraInfo = if (items.isEmpty()) parcel.value.total.text else "",
                size = .6f
            )
            Form.Input.Money(
                icon = Theme.Icons.Percent.icon,
                placeholder = getFeesInputPlaceholder(),
                value = getFeesInputValue(),
                onValueChange = {
                    parcel.value =
                        parcel.value.copy(fees = Money.resolve(it))
                    feesInput.value = TextFieldValue(
                        text = parcel.value.fees.text,
                        selection = TextRange(parcel.value.fees.text.length)
                    )
                }
            )
        }
}