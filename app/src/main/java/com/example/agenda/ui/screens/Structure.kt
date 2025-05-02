package com.example.agenda.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agenda.app.App
import com.example.agenda.app.objects.MonthYearObject
import com.example.agenda.ui.Theme
import com.example.agenda.ui.system.Navigation
import com.example.agenda.ui.viewmodels.StructureVM
import kotlinx.coroutines.runBlocking


@Composable
fun FloatButton(onClick: () -> Unit, icon: ImageVector){
    FloatingActionButton(
        containerColor = Theme.Colors.D.color,
        contentColor = Theme.Colors.A.color,
        onClick = onClick,
    ) {
        Icon(
            icon,
            contentDescription = "Floating Button",
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Structure(content: (@Composable () -> Unit)) {
    val vm: StructureVM = viewModel()
    val buttonsVisible by vm.buttonsVisible.collectAsState()
    val fadeInOutMillis = vm.fadeInOutMillis
    val distanceFloatingButtons = vm.distanceFloatingButtons
    val backgroundColor by animateColorAsState(
        targetValue = if (buttonsVisible) Theme.Colors.D.color else Theme.Colors.A.color,
        animationSpec = tween(durationMillis = fadeInOutMillis)
    )
    val iconColor by animateColorAsState(
        targetValue = if (buttonsVisible) Theme.Colors.A.color else Theme.Colors.D.color,
        animationSpec = tween(durationMillis = fadeInOutMillis)
    )

    val angle: Float by animateFloatAsState(
        targetValue = if (buttonsVisible) 45f else 0f,
        animationSpec = tween(durationMillis = fadeInOutMillis),
        label = "rotate"
    )

    val floatingButtonColors = listOf(
        backgroundColor,
        iconColor
    )
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current as Activity

    SideEffect {
        context.window.statusBarColor = Theme.Colors.A.color.toArgb()
        context.window.navigationBarColor = Theme.Colors.A.color.toArgb()
    }

    Scaffold(
        modifier = Modifier.background(Theme.Colors.A.color),
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(distanceFloatingButtons),
            ) {
                AnimatedVisibility(
                    visible = buttonsVisible,
                    enter = fadeIn(animationSpec = tween(fadeInOutMillis)),
                    exit = fadeOut(animationSpec = tween(fadeInOutMillis))
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(distanceFloatingButtons),
                    ) {

                        FloatButton(onClick = {
                            Navigation.navController.navigate(EventCategories.Screens.AllEventCategories.Route())
                            vm.toggleButtons()
                        }, icon = Theme.Icons.EventCategory.icon)

                        FloatButton(onClick = {
                            Navigation.navController.navigate(TransactionCategories.Screens.AllTransactionCategories.Route())
                            vm.toggleButtons()
                        }, icon = Theme.Icons.TransactionCategory.icon)
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                runBlocking {
                                    App.UseCases.backup.restore()
                                }
                            },
                        ) {
                            Icon(
                                Theme.Icons.Restore.icon,
                                contentDescription = "Restore",
                            )
                        }
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                runBlocking {
                                    App.UseCases.backup.execute(Unit)
                                }
                            },
                        ) {
                            Icon(
                                Theme.Icons.Backup.icon,
                                contentDescription = "Backup",
                            )
                        }
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                Navigation.navController.navigate(Navigation.CreateBankRoute())
                                vm.toggleButtons()

                            },

                            ) {
                            Icon(
                                Theme.Icons.Bank.icon,
                                contentDescription = "Adicionar Banco",
                            )
                        }
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                Navigation.navController.navigate(Navigation.CreateGoalRoute())
                                vm.toggleButtons()

                            }
                        ) {
                            Icon(
                                Theme.Icons.Goal.icon,
                                contentDescription = "Adicionar Objetivo",
                            )
                        }
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                Navigation.navController.navigate(Navigation.CreateTransactionRoute())
                                vm.toggleButtons()

                            },

                            ) {
                            Icon(
                                Theme.Icons.Transaction.icon,
                                contentDescription = "Adicionar Transação",
                            )
                        }
                        FloatingActionButton(
                            containerColor = Theme.Colors.D.color,
                            contentColor = Theme.Colors.A.color,
                            onClick = {
                                Navigation.navController.navigate(Navigation.CreateEventRoute(null))
                                vm.toggleButtons()
                            },
                        ) {
                            Icon(
                                Theme.Icons.Event.icon,
                                contentDescription = "Adicionar Evento",
                            )
                        }
                    }
                }

                FloatingActionButton(
                    containerColor = floatingButtonColors[0],
                    contentColor = floatingButtonColors[1],
                    onClick = {
                        vm.toggleButtons()
                    },

                    ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Adicionar evento",
                        modifier = Modifier.rotate(angle)
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Theme.Colors.A.color)
        ) {
            AnimatedVisibility(
                visible = buttonsVisible,
                enter = fadeIn(animationSpec = tween(fadeInOutMillis)),
                exit = fadeOut(animationSpec = tween(fadeInOutMillis))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            vm.toggleButtons()
                        }) {}
            }
            AnimatedVisibility(
                visible = !buttonsVisible,
                enter = fadeIn(animationSpec = tween(fadeInOutMillis)),
                exit = fadeOut(animationSpec = tween(fadeInOutMillis))
            ) { content() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    vm: StructureVM,
    pagerState: PagerState? = null,
    data: List<MonthYearObject>? = null,
    indexOffset: Int? = null,
) {
    val buttonsVisible by vm.buttonsVisible.collectAsState()
    val fadeInOutMillis = vm.fadeInOutMillis
    AnimatedVisibility(
        visible = !buttonsVisible,
        enter = fadeIn(animationSpec = tween(fadeInOutMillis)),
        exit = fadeOut(animationSpec = tween(fadeInOutMillis)),
        modifier = Modifier
            .background(Theme.Colors.A.color),
    ) {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = Theme.Colors.A.color,
                scrolledContainerColor = Theme.Colors.B.color,
                navigationIconContentColor = Theme.Colors.D.color,
                titleContentColor = Theme.Colors.D.color,
                actionIconContentColor = Theme.Colors.D.color
            ),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {

                    Text(
                        text = App.UI.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {


                    IconButton(onClick = { Navigation.navController.navigate(Navigation.BanksRoute()) }) {
                        Icon(Theme.Icons.Bank.icon, contentDescription = "Contas")
                    }


                    IconButton(onClick = { Navigation.navController.navigate(Navigation.GoalsRoute()) }) {
                        Icon(Theme.Icons.Goal.icon, contentDescription = "Objetivos")
                    }


                    IconButton(onClick = {
                        Navigation.navController.navigate(Navigation.TransactionsRoute())
                    }) {
                        Icon(
                            Theme.Icons.Transaction.icon,
                            contentDescription = "Transações"
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


                            if (Navigation.isActualRoute(
                                    Navigation.HomeRoute().toString()
                                ) && pagerState != null && data != null && indexOffset != null
                            ) {
                                runBlocking {
                                    data.find { it.monthAndYear.month == App.Time.today.month && it.monthAndYear.year == App.Time.today.year }
                                        .also {
                                            if (it !== null) {
                                                pagerState.scrollToPage(data.size / 400 + indexOffset + 1)
                                            }
                                        }
                                }
                            } else {
                                Navigation.navController.navigate(Navigation.HomeRoute())
                            }
                        }) {
                            Icon(Theme.Icons.Event.icon, contentDescription = "Calendário")
                        }
                    }
                }
            },
            modifier = Modifier
                .height(45.dp)
                .background(Theme.Colors.A.color),
        )
    }
}