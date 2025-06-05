package com.example.agenda.ui.system

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.agenda.ui.Theme
import com.example.agenda.ui.screens.Banks
import com.example.agenda.ui.screens.EventCategories
import com.example.agenda.ui.screens.Goals
import com.example.agenda.ui.screens.Home
import com.example.agenda.ui.screens.Profile
import com.example.agenda.ui.screens.SingleBank
import com.example.agenda.ui.screens.SingleDay
import com.example.agenda.ui.screens.SingleGoal
import com.example.agenda.ui.screens.SingleTransaction
import com.example.agenda.ui.screens.TransactionCategories
import com.example.agenda.ui.screens.Transactions
import kotlinx.serialization.Serializable

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

    inline fun <reified T> args(back: NavBackStackEntry): T {
        return back.toRoute<T>()
    }

    @Composable
    fun Host() {
        val navController = rememberNavController()
        this.navController = navController

        NavHost(
            navController = navController,
            startDestination = HomeRoute(),
            modifier = Modifier.background(Theme.Colors.A.color)
        ) {
            composable<HomeRoute> {
                Home()
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
            composable<Transactions.Screens.MonthlyTransactions.Route>
            { Transactions.Screens.MonthlyTransactions.Screen() }

            composable<BanksRoute> {
                Banks()
            }
            composable<TransactionCategories.Screens.AllTransactionCategories.Route>
            { TransactionCategories.Screens.AllTransactionCategories.Screen() }

            composable<TransactionCategories.Screens.SingleTransactionCategory.Route>
            {
                TransactionCategories.Screens.SingleTransactionCategory.Screen(
                    args<TransactionCategories.Screens.SingleTransactionCategory.Route>(
                        it
                    )
                )
            }

            composable<Profile.Route>
            { Profile.Screen() }
            composable<EventCategories.Screens.AllEventCategories.Route>
            { EventCategories.Screens.AllEventCategories.Screen() }
            composable<EventCategories.Screens.SingleEventCategory.Route>
            {
                EventCategories.Screens.SingleEventCategory.Screen(
                    args<EventCategories.Screens.SingleEventCategory.Route>(
                        it
                    )
                )
            }
            composable<GoalsRoute> {
                Goals()
            }
        }


    }
}
