package com.example.agenda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.domain.entities.TransactionCategory
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


object TransactionCategories {


    object Default {
        object Goal {
            const val NAME_AND_ID = "Metas"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }

        object Others {
            const val NAME_AND_ID = "Outros"
            val BACKGROUND_COLOR = Theme.Colors.D.color.value.toString()
            val TEXT_COLOR = Theme.Colors.A.color.value.toString()
        }
    }

    object Screens {
        object AllTransactionCategories {
            @Serializable
            class Route
            class VM : ViewModel() {
                val transactionCategories =
                    MutableStateFlow(mutableListOf<TransactionCategoryEntity>())

                init {
                    runBlocking {
                        transactionCategories.value =
                            App.Repositories.transactionCategoryRepository.getAll().toMutableList()
                    }
                }
            }

            @Composable
            fun Screen() {
                val vm: VM = viewModel()
                val structureVM: StructureVM = viewModel()
                val transactionCategories by vm.transactionCategories.collectAsState()
                App.UI.title = "Categorías de Transações"
                Column {
                    Header(structureVM)
                    BTN(
                        onClick = {
                            Navigation.navController.navigate(CreateTransactionCategory.Route())
                        }, text = "Criar Categoria"
                    )
                    LazyColumn {
                        items(transactionCategories) {
                            Box(
                                modifier = Modifier
                                    .background(Color(it.backgroundColor.toULong()))
                                    .padding(4.dp)
                                    .clickable {
                                        Navigation.navController.navigate(
                                            SingleTransactionCategory.Route(
                                                it.id!!
                                            )
                                        )
                                    }
                            ) {
                                TXT(it.name, color = Color(it.textColor.toULong()))
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }

                }
            }
        }

        object CreateTransactionCategory {
            @Serializable
            data class Route(val id: String? = null)

            @Composable
            fun Screen(args: Route) {
                val structureVM: StructureVM = viewModel()
                var name by remember { mutableStateOf("") }
                val textColor = remember { mutableStateOf(Theme.Colors.A.color.value.toString()) }
                val backgroundColor = remember { mutableStateOf(Theme.Colors.B.color.value.toString()) }
                var isColorPickerVisible by remember { mutableStateOf(false) }


                var transactionCategory by remember {
                    mutableStateOf<TransactionCategoryEntity?>(
                        null
                    )
                }
                LaunchedEffect(Unit) {
                    if (args.id != null) {
                        runBlocking {
                            transactionCategory =
                                App.Repositories.transactionCategoryRepository.getById(args.id)

                            name = transactionCategory!!.name
                            textColor.value = transactionCategory!!.textColor
                            backgroundColor.value = transactionCategory!!.backgroundColor
                        }
                    }
                }

                if (isColorPickerVisible) ColorPicker.Component({isColorPickerVisible = false},backgroundColor, textColor)
                App.UI.title = "Criar Categoria de Transação"
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
                                        backgroundColor = backgroundColor.value
                                    )
                                    App.Repositories.transactionCategoryRepository.create(
                                        t
                                    )
                                }
                                Navigation.navController.navigate(AllTransactionCategories.Route())
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
                                        backgroundColor = backgroundColor.value
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
            }

        }

        object SingleTransactionCategory {
            @Serializable
            data class Route(val id: String)

            class VM : ViewModel() {

            }

            @Composable
            fun Screen(args: Route) {
                var transactionCategory by remember {
                    mutableStateOf<TransactionCategoryEntity?>(
                        null
                    )
                }

                runBlocking {
                    transactionCategory =
                        App.Repositories.transactionCategoryRepository.getById(args.id)
                }

                val structureVM: StructureVM = viewModel()
                App.UI.title = "Categoria de Transação"
                Column {

                    Header(structureVM)
                    Column {
                        Box(Modifier.background(color = Color(transactionCategory?.backgroundColor!!.toULong()))) {

                            TXT(
                                s = "Nome: ${transactionCategory?.name}",
                                color = Color(transactionCategory?.textColor!!.toULong())
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
//                        BTN(onClick = {
//                            runBlocking {
//                                App.Repositories.transactionCategoryRepository.delete(
//                                    transactionCategory!!
//                                )
//                                App.Repositories.transactionRepository.deleteByCategory(
//                                    transactionCategory!!.id!!
//                                )
//                            }
//                            Navigation.navController.navigate(AllTransactionCategories.Route())
//                        }) {
//                            TXT("Deletar", color = Theme.Colors.A.color)
//                        }
                            BTN(onClick = {
                                Navigation.navController.navigate(
                                    CreateTransactionCategory.Route(
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
