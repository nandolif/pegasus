package com.example.agenda.ui.system

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.agenda.ui.screens.Banks
import com.example.agenda.ui.screens.CreateBank
import com.example.agenda.ui.screens.CreateEvent
import com.example.agenda.ui.screens.CreateGoal
import com.example.agenda.ui.screens.CreateTransaction
import com.example.agenda.ui.screens.EventCategories
import com.example.agenda.ui.screens.Goals
import com.example.agenda.ui.screens.Home
import com.example.agenda.ui.screens.SingleBank
import com.example.agenda.ui.screens.SingleDay
import com.example.agenda.ui.screens.SingleGoal
import com.example.agenda.ui.screens.SingleTransaction
import com.example.agenda.ui.screens.Structure
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.screens.Transactions
import kotlinx.serialization.Serializable
import java.lang.reflect.TypeVariable

object Navigation {
    @Serializable
    class HomeRoute

    @Serializable
    data class CreateEventRoute(val date: String?)

    @Serializable
    data class SingleBankRoute(val id: String)

    @Serializable
    data class SingleTransactionRoute(val id: String)

    @Serializable
    data class SingleGoalRoute(val id: String)

    @Serializable
    data class SingleDayRoute(val day: Int, val month: Int, val year: Int)

    @Serializable
    class TransactionsRoute

    @Serializable
    data class CreateTransactionRoute(val id: String? = null)

    @Serializable
    class BanksRoute

    @Serializable
    data class CreateBankRoute(val id: String? = null)

    @Serializable
    class GoalsRoute

    @Serializable
    class CreateGoalRoute


    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController



    fun isActualRoute(route: String): Boolean {
        val actualRoute = navController.currentDestination?.route ?: HomeRoute().toString()
        var a = actualRoute.split(".").last()

        if (a.contains("@")) {
            a = a.split("@").first()
        }
        return route.contains(a)
    }
    inline fun<reified T> args(back: NavBackStackEntry): T {
        return back.toRoute<T>()
    }
    @Composable
    fun Host() {
        val navController = rememberNavController()
        this.navController = navController

        Structure(
            content = {
                NavHost(
                    navController = navController,
                    startDestination = HomeRoute(),
                ) {
                    composable<HomeRoute> {
                        Home()
                    }
                    composable<CreateEventRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<CreateEventRoute>()
                        CreateEvent(eventDate = args.date)
                    }
                    composable<SingleBankRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<SingleBankRoute>()
                        SingleBank(args.id)
                    }
                    composable<SingleGoalRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<SingleGoalRoute>()
                        SingleGoal(args.id)
                    }
                    composable<SingleTransactionRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<SingleTransactionRoute>()
                        SingleTransaction(args.id)
                    }
                    composable<SingleDayRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<SingleDayRoute>()
                        SingleDay(args.day, args.month, args.year)
                    }
                    composable<TransactionsRoute> {
                        Transactions()
                    }
                    composable<CreateTransactionRoute> {
                        val args = it.toRoute<CreateTransactionRoute>()
                        CreateTransaction(args.id)
                    }
                    composable<BanksRoute> {
                        Banks()
                    }
                    composable<TransactionCategories.Screens.AllTransactionCategories.Route>
                    { TransactionCategories.Screens.AllTransactionCategories.Screen() }
                    composable<TransactionCategories.Screens.CreateTransactionCategory.Route>
                    { TransactionCategories.Screens.CreateTransactionCategory.Screen(args<TransactionCategories.Screens.CreateTransactionCategory.Route>(it)) }
                    composable<TransactionCategories.Screens.SingleTransactionCategory.Route>
                    {TransactionCategories.Screens.SingleTransactionCategory.Screen(args<TransactionCategories.Screens.SingleTransactionCategory.Route>(it))}

                    composable<EventCategories.Screens.AllEventCategories.Route>
                    { EventCategories.Screens.AllEventCategories.Screen() }
                    composable<EventCategories.Screens.CreateEventCategory.Route>
                    { EventCategories.Screens.CreateEventCategory.Screen(args<EventCategories.Screens.CreateEventCategory.Route>(it)) }
                    composable<EventCategories.Screens.SingleEventCategory.Route>
                    { EventCategories.Screens.SingleEventCategory.Screen(args<EventCategories.Screens.SingleEventCategory.Route>(it)) }

                    composable<CreateBankRoute> {
                        val args = it.toRoute<CreateBankRoute>()
                        CreateBank(args.id)
                    }
                    composable<GoalsRoute> {
                        Goals()
                    }
                    composable<CreateGoalRoute> {
                        CreateGoal()
                    }

                }
            })


    }
}