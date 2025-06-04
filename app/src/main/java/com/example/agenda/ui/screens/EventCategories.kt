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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import com.example.agenda.ui.component.TXT
import com.example.agenda.ui.component.form.CreateEventCategoryForm
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
                val eventCategories = MutableStateFlow(mutableListOf<EventCategory>())

                fun fetchData() {
                    runBlocking {
                        eventCategories.value = App.Repositories.eventCategoryRepository.getAll().toMutableList()
                    }
                }
                init {
                    fetchData()
                }
            }

            @Composable
            fun Screen() {
                val vm: VM = viewModel()
                val structureVM: StructureVM = viewModel()
                val eventCategories by vm.eventCategories.collectAsState()
                val createEventCategoryForm = CreateEventCategoryForm(callback = {vm.fetchData()})
                Structure.Wrapper(header = { Structure.Header("Categorias de Eventos") }, bottom = {Structure.BottomBar(createEventCategoryForm)}) {
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
        object SingleEventCategory {
            @Serializable
            data class Route(val id: String)

            @Composable
            fun Screen(args: Route) {
                var eventCategory by remember {
                    mutableStateOf<EventCategory?>(
                        null
                    )
                }
                runBlocking {
                    eventCategory =
                        App.Repositories.eventCategoryRepository.getById(args.id)
                }

                val structureVM: StructureVM = viewModel()
                Structure.Wrapper(
                    header = {Structure.Header("Categoria de Evento")},
                    bottom = {Structure.BottomBar(CreateEventCategoryForm(eventCategory?.id , callback = {}), Icons.Default.Edit)}
                ) {

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
                        }
                    }
                }
            }

        }
    }
}