package com.example.agenda.ui.component

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agenda.ui.Theme
import com.example.agenda.ui.screens.Transactions
import com.example.agenda.ui.system.Navigation


//
//@Composable
//fun DefaultFloatingButton() {
//    val vm: StructureVM = viewModel()
//    val buttonsVisible by vm.buttonsVisible.collectAsState()
//    val fadeInOutMillis = vm.fadeInOutMillis
//    val distanceFloatingButtons = vm.distanceFloatingButtons
//    val angle: Float by animateFloatAsState(
//        targetValue = if (buttonsVisible) 45f else 0f,
//        animationSpec = tween(durationMillis = fadeInOutMillis),
//        label = "rotate"
//    )
//    val backgroundColor by animateColorAsState(
//        targetValue = if (buttonsVisible) Theme.Colors.D.color else Theme.Colors.A.color,
//        animationSpec = tween(durationMillis = fadeInOutMillis)
//    )
//    val iconColor by animateColorAsState(
//        targetValue = if (buttonsVisible) Theme.Colors.A.color else Theme.Colors.D.color,
//        animationSpec = tween(durationMillis = fadeInOutMillis)
//    )
//
//    val createTransactionBottomSheet = CreateTransactionForm()
//    Column(
//        verticalArrangement = Arrangement.spacedBy(distanceFloatingButtons),
//    ) {
//        AnimatedVisibility(
//            visible = buttonsVisible,
//            enter = fadeIn(animationSpec = tween(fadeInOutMillis)),
//            exit = fadeOut(animationSpec = tween(fadeInOutMillis))
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(distanceFloatingButtons),
//            ) {
//
//                FloatButton(onClick = {
//                    Navigation.navController.navigate(EventCategories.Screens.AllEventCategories.Route())
//                    vm.toggleButtons()
//                }, icon = Theme.Icons.EventCategory.icon)
//
//                FloatButton(onClick = {
//                    Navigation.navController.navigate(TransactionCategories.Screens.AllTransactionCategories.Route())
//                    vm.toggleButtons()
//                }, icon = Theme.Icons.TransactionCategory.icon)
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//                        runBlocking {
//                            App.UseCases.backup.restore()
//                        }
//                    },
//                ) {
//                    Icon(
//                        Theme.Icons.Restore.icon,
//                        contentDescription = "Restore",
//                    )
//                }
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//                        runBlocking {
//                            App.UseCases.backup.execute(Unit)
//                        }
//                    },
//                ) {
//                    Icon(
//                        Theme.Icons.Backup.icon,
//                        contentDescription = "Backup",
//                    )
//                }
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//
//                    },
//
//                    ) {
//                    Icon(
//                        Theme.Icons.Bank.icon,
//                        contentDescription = "Adicionar Banco",
//                    )
//                }
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//                        Navigation.navController.navigate(Navigation.CreateGoalRoute())
//                        vm.toggleButtons()
//
//                    }
//                ) {
//                    Icon(
//                        Theme.Icons.Goal.icon,
//                        contentDescription = "Adicionar Objetivo",
//                    )
//                }
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//                        createTransactionBottomSheet()
//                        vm.toggleButtons()
//                    },
//
//                    ) {
//                    Icon(
//                        Theme.Icons.Transaction.icon,
//                        contentDescription = "Adicionar Transação",
//                    )
//                }
//                FloatingActionButton(
//                    containerColor = Theme.Colors.D.color,
//                    contentColor = Theme.Colors.A.color,
//                    onClick = {
//                        Navigation.navController.navigate(Navigation.CreateEventRoute(null))
//                        vm.toggleButtons()
//                    },
//                ) {
//                    Icon(
//                        Theme.Icons.Event.icon,
//                        contentDescription = "Adicionar Evento",
//                    )
//                }
//            }
//        }
//
//        FloatButton(
//            { vm.toggleButtons() },
//            Icons.Default.Add,
//        )
//
//    }
//}


object Structure {
    @Composable
    fun Wrapper(
        header: (@Composable () -> Unit)? = null,
        bottom: (@Composable () -> Unit)? = null,
        content: (@Composable () -> Unit),
    ) {
        val context = LocalContext.current as Activity

        SideEffect {
            context.window.statusBarColor = Theme.Colors.A.color.toArgb()
            context.window.navigationBarColor = Theme.Colors.A.color.toArgb()
        }
        Scaffold(
            topBar = {
                if (header != null) {
                    header()
                }
            },
            bottomBar = {
                if (bottom != null) bottom()
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(Theme.Colors.A.color)
                    .fillMaxSize()
            ) {
                content()
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Header(
        title: String,
        actions: (@Composable () -> Unit)? = null,
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
                        text = title,
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
                    if (actions != null) {
                        actions()

                    }
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Conta")
                    }
                }
            },
            modifier = Modifier
                .height(45.dp)
                .background(Theme.Colors.A.color),
        )
    }

    @Composable
    fun BottomBar(callback: (() -> Unit)? = null, icon: ImageVector = Icons.Default.Add) {
        Column(
            modifier = Modifier.background(Theme.Colors.A.color),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(thickness = 1.dp, color = Theme.Colors.B.color)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(onClick = { Navigation.navController.navigate(Navigation.BanksRoute()) }) {
                    Icon(
                        Theme.Icons.Bank.icon,
                        contentDescription = "Contas",
                        tint = Theme.Colors.D.color
                    )
                }

                IconButton(onClick = { Navigation.navController.navigate(Navigation.GoalsRoute()) }) {
                    Icon(
                        Theme.Icons.Goal.icon,
                        contentDescription = "Objetivos",
                        tint = Theme.Colors.D.color
                    )
                }

                if (callback != null) {
                    IconButton(onClick = { callback() }) {
                        Icon(
                            icon,
                            contentDescription = "Adicionar",
                            tint = Theme.Colors.D.color
                        )
                    }
                }

                IconButton(onClick = { Navigation.navController.navigate(Transactions.Screens.MonthlyTransactions.Route()) }) {
                    Icon(
                        Theme.Icons.Transaction.icon,
                        contentDescription = "Transações",
                        tint = Theme.Colors.D.color
                    )
                }

                IconButton(onClick = { Navigation.navController.navigate(Navigation.HomeRoute()) }) {
                    Icon(
                        Theme.Icons.Event.icon,
                        contentDescription = "Eventos",
                        tint = Theme.Colors.D.color
                    )
                }
            }
        }
    }
}
