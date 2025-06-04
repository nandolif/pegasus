package com.example.agenda.ui.component.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.ColorPicker
import com.example.agenda.ui.component.EDM
import com.example.agenda.ui.component.IconPicker
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.screens.TransactionCategories.Screens.AllTransactionCategories
import com.example.agenda.ui.screens.Transactions
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.runBlocking


fun create() {}

@Composable
fun CreateTransactionCategoryForm(id: String? = null, callback: () -> Unit): () -> Unit {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(Transactions.Type.EXPENSE) }
    val textColor = remember { mutableStateOf(ColorPicker.random().value.toString()) }
    val backgroundColor = remember { mutableStateOf(ColorPicker.random().value.toString()) }
    val icon = remember { mutableStateOf("") }
    var transactionCategory by remember {
        mutableStateOf<TransactionCategory?>(
            null
        )
    }
    LaunchedEffect(Unit) {
        if (id != null) {
            runBlocking {
                transactionCategory =
                    App.Repositories.transactionCategoryRepository.getById(id)

                name = transactionCategory!!.name
                textColor.value = transactionCategory!!.textColor
                backgroundColor.value = transactionCategory!!.backgroundColor
                type = transactionCategory!!.type
            }
        }
    }

    val toggleColorPicker = ColorPicker.Component(backgroundColor, textColor)
    val toggleIconPicker = IconPicker.Component(icon)

    val toggle = BottomSheet.Wrapper { tgl ->
        OTF(
            label = "Nome",
            value = name,
            onValueChange = { name = it }
        )
        EDM<Transactions.Type>(
            selected = type,
            onSelectedChange = { type = it },
            label = "Tipo",
        )
        BTN(
            onClick = toggleColorPicker,
            text = "Selecionar Cor",
            containerColor = Color(backgroundColor.value.toULong()),
            textColor = Color(textColor.value.toULong())
        )

        BTN(
            onClick = toggleIconPicker,
            text = "Selecionar Icone",
        )
        if (transactionCategory == null) {
            BTN(
                onClick = {
                    runBlocking {
                        if (name.isEmpty()) return@runBlocking
                        val t = TransactionCategory(
                            id = null,
                            name = name,
                            created_at = null,
                            updated_at = null,
                            textColor = textColor.value,
                            backgroundColor = backgroundColor.value,
                            type = type
                        )
                        App.Repositories.transactionCategoryRepository.create(
                            t
                        )
                    }
                    callback()
                    tgl()
                }, text = "Salvar"
            )
        } else {
            BTN(
                onClick = {
                    runBlocking {
                        if (name.isEmpty()) return@runBlocking
                        val newTransactionCategory = TransactionCategory(
                            id = transactionCategory!!.id,
                            name = name,
                            created_at = transactionCategory!!.created_at,
                            updated_at = App.Time.now(),
                            textColor = textColor.value,
                            backgroundColor = backgroundColor.value,
                            type = type
                        )
                        App.Repositories.transactionCategoryRepository.update(
                            newTransactionCategory
                        )
                    }
                    Navigation.navController.navigate(AllTransactionCategories.Route())
                }, text = "Atualizar"
            )
        }
    }
    return toggle
}
