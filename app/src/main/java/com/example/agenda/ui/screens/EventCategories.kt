package com.example.agenda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agenda.app.App
import com.example.agenda.app.entities.EventCategoryEntity
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.ColorPicker
import com.example.agenda.ui.component.OTF
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

object EventCategories {
    object Default {
        object Others {
            const val NAME_AND_ID = "Outros"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }
    }
    object Screens {
        object AllEventCategories {
            @Serializable
            class Route


            class VM : ViewModel() {
                val eventCategories = MutableStateFlow(mutableListOf<EventCategoryEntity>())

                init {
                    runBlocking {
                        eventCategories.value = App.Repositories.eventCategoryRepository.getAll().toMutableList()
                    }
                }
            }

            @Composable
            fun Screen() {
                val vm: VM = viewModel()
                val structureVM: StructureVM = viewModel()
                App.UI.title = "Categorias de Eventos"

                val eventCategories by vm.eventCategories.collectAsState()

                Column {
                    Header(structureVM)
                    BTN(
                        "Adicionar Categoria de Evento",
                        { Navigation.navController.navigate(CreateEventCategory.Route()) })
                    Spacer(Modifier.height(10.dp))
                    LazyColumn {
                        items(eventCategories) {
                            Box(
                                Modifier
                                    .background(Color(it.backgroundColor.toULong()))
                                    .clickable {
                                        Navigation.navController.navigate(
                                            SingleEventCategory.Route(it.id!!)
                                        )
                                    }
                            ) {
                                TXT(it.name, Color(it.textColor.toULong()))
                            }

                        }
                    }
                }
            }
        }
        object CreateEventCategory {
            @Serializable
            data class Route(val id: String? = null)

            @Composable
            fun Screen(args: Route) {
                val structureVM: StructureVM = viewModel()
                var name by remember { mutableStateOf("") }
                val textColor = remember { mutableStateOf(Theme.Colors.A.color.value.toString()) }
                val backgroundColor =
                    remember { mutableStateOf(Theme.Colors.D.color.value.toString()) }
                var isColorPickerVisible by remember { mutableStateOf(false) }


                var eventCategory by remember {
                    mutableStateOf<EventCategoryEntity?>(
                        null
                    )
                }
                LaunchedEffect(Unit) {
                    if (args.id != null) {
                        runBlocking {
                            eventCategory =
                                App.Repositories.eventCategoryRepository.getById(args.id)

                            name = eventCategory!!.name
                            textColor.value = eventCategory!!.textColor
                            backgroundColor.value = eventCategory!!.backgroundColor
                        }
                    }
                }

                if (isColorPickerVisible) ColorPicker.Component(
                    { isColorPickerVisible = false },
                    backgroundColor,
                    textColor
                )
                App.UI.title = "Criar Categoria de Evento"
                Column {
                    Header(structureVM)
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
                                Navigation.navController.navigate(AllEventCategories.Route())
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
                                Navigation.navController.navigate(AllEventCategories.Route())
                            }, text = "Atualizar"
                        )
                    }

                }
            }

        }
        object SingleEventCategory {
            @Serializable
            data class Route(val id: String)

            class VM : ViewModel() {

            }

            @Composable
            fun Screen(args: Route) {
                var eventCategory by remember {
                    mutableStateOf<EventCategoryEntity?>(
                        null
                    )
                }

                runBlocking {
                    eventCategory =
                        App.Repositories.eventCategoryRepository.getById(args.id)
                }

                val structureVM: StructureVM = viewModel()
                App.UI.title = "Categoria de Transação"
                Column {

                    Header(structureVM)
                    Column {
                        Box(Modifier.background(color = Color(eventCategory?.backgroundColor!!.toULong()))) {

                            TXT(
                                s = "Nome: ${eventCategory?.name}",
                                color = Color(eventCategory?.textColor!!.toULong())
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
//                        BTN(onClick = {
//                            runBlocking {
//                                App.Repositories.eventCategoryRepository.delete(
//                                    eventCategory!!
//                                )
//                                App.Repositories.transactionRepository.deleteByCategory(
//                                    eventCategory!!.id!!
//                                )
//                            }
//                            Navigation.navController.navigate(AllTransactionCategories.Route())
//                        }) {
//                            TXT("Deletar", color = Theme.Colors.A.color)
//                        }
                            BTN(onClick = {
                                Navigation.navController.navigate(
                                    CreateEventCategory.Route(
                                        args.id
                                    )
                                )
                            }, text = "Atualizar")
                        }
                    }
                }
            }

        }
    }
}