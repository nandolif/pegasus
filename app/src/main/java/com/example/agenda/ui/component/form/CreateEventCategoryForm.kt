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
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.ColorPicker
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.screens.EventCategories.Screens.AllEventCategories
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.runBlocking

@Composable
fun CreateEventCategoryForm(id: String? = null, callback: ()->Unit): () -> Unit {
    val structureVM: StructureVM = viewModel()
    var name by remember { mutableStateOf("") }
    val textColor = remember { mutableStateOf(Theme.Colors.A.color.value.toString()) }
    val backgroundColor =
        remember { mutableStateOf(Theme.Colors.D.color.value.toString()) }
    var isColorPickerVisible by remember { mutableStateOf(false) }


    var eventCategory by remember {
        mutableStateOf<EventCategory?>(
            null
        )
    }
    LaunchedEffect(Unit) {
        if (id != null) {
            runBlocking {
                eventCategory =
                    App.Repositories.eventCategoryRepository.getById(id)

                name = eventCategory!!.name
                textColor.value = eventCategory!!.textColor
                backgroundColor.value = eventCategory!!.backgroundColor
            }
        }
    }

    if (isColorPickerVisible) ColorPicker.Component(
        backgroundColor,
        textColor
    )
    val toggle = BottomSheet.Wrapper { t->
        Spacer(Modifier.height(8.dp))
        OTF(
            label = "Nome",
            value = name,
            onValueChange = { name = it }
        )
        BTN(
            onClick = { isColorPickerVisible = !isColorPickerVisible },
            text = "Selecionar Cor",
            containerColor = Color(backgroundColor.value.toULong()),
            textColor = Color(textColor.value.toULong())
        )
        if (eventCategory == null) {
            BTN(
                onClick = {
                    runBlocking {
                        if (name.isEmpty()) return@runBlocking
                        val t = EventCategory(
                            id = null,
                            name = name,
                            created_at = null,
                            updated_at = null,
                            textColor = textColor.value,
                            backgroundColor = backgroundColor.value
                        )
                        App.Repositories.eventCategoryRepository.create(
                            t
                        )
                    }
                    callback()
                    t()
                }, text = "Salvar"
            )
        } else {
            BTN(
                onClick = {
                    runBlocking {
                        if (name.isEmpty()) return@runBlocking
                        val newEventCateogory = EventCategory(
                            id = eventCategory!!.id,
                            name = name,
                            created_at = eventCategory!!.created_at,
                            updated_at = App.Time.now(),
                            textColor = textColor.value,
                            backgroundColor = backgroundColor.value
                        )
                        App.Repositories.eventCategoryRepository.update(
                            newEventCateogory
                        )
                    }
                    t()
                }, text = "Atualizar"
            )
        }

    }
    return toggle
}
