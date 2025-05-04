package com.example.agenda.app

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.agenda.app.helps.Date
import com.example.agenda.domain.entities.Bank
import com.example.agenda.domain.entities.Event
import com.example.agenda.domain.entities.EventCategory
import com.example.agenda.domain.entities.Goal
import com.example.agenda.domain.entities.MyObjectBox
import com.example.agenda.domain.entities.Transaction
import com.example.agenda.domain.entities.TransactionCategory
import com.example.agenda.domain.repositories.box.BankBoxRepository
import com.example.agenda.domain.repositories.box.EventBoxRepository
import com.example.agenda.domain.repositories.box.EventCategoryBoxRepository
import com.example.agenda.domain.repositories.box.GoalBoxRepository
import com.example.agenda.domain.repositories.box.TransactionBoxRepository
import com.example.agenda.domain.repositories.box.TransactionCategoryBoxRepository
import com.example.agenda.domain.repositories.memory.BankInMemoryRepository
import com.example.agenda.domain.repositories.memory.EventInMemoryRepository
import com.example.agenda.domain.repositories.memory.GoalInMemoryRepository
import com.example.agenda.domain.repositories.memory.TransactionInMemoryRepository
import com.example.agenda.domain.usecases.Backup
import com.example.agenda.domain.usecases.CreateBank
import com.example.agenda.domain.usecases.CreateEvent
import com.example.agenda.domain.usecases.CreateGoal
import com.example.agenda.domain.usecases.CreateRecurrenceEvent
import com.example.agenda.domain.usecases.CreateRecurrenceTransaction
import com.example.agenda.domain.usecases.CreateTransaction
import com.example.agenda.domain.usecases.DeleteBank
import com.example.agenda.domain.usecases.DeleteEvent
import com.example.agenda.domain.usecases.DeleteGoal
import com.example.agenda.domain.usecases.DeleteTransaction
import com.example.agenda.domain.usecases.GetAllBanks
import com.example.agenda.domain.usecases.GetAllGoals
import com.example.agenda.domain.usecases.GetAllTransactions
import com.example.agenda.domain.usecases.GetBank
import com.example.agenda.domain.usecases.GetBankRaw
import com.example.agenda.domain.usecases.GetEventsByDate
import com.example.agenda.domain.usecases.GetGoal
import com.example.agenda.domain.usecases.GetTransaction
import com.example.agenda.domain.usecases.GetTransactionsByGoal
import com.example.agenda.domain.usecases.GetWeekDataInFutureOrPast
import com.example.agenda.domain.usecases.GetWeeksData
import com.example.agenda.domain.usecases.UpdateBank
import com.example.agenda.domain.usecases.UpdateTransaction
import com.example.agenda.ui.system.Cache
import com.example.agenda.ui.system.Notify
import io.objectbox.Box
import io.objectbox.BoxStore

object App {
    object Time {
        fun now() = System.currentTimeMillis()
        val today = Date.getToday()
        const val ZONE_ID = "GMT-3"
        const val PATTERN = "dd/MM/yyyy"
    }

    object Config {
        const val DEBUG = false
    }

    object Database {
        private val boxStore: BoxStore = MyObjectBox.builder().androidContext(UI.context).build()
        val bankBox: Box<Bank> = boxStore.boxFor(Bank::class.java)
        val transactionBox: Box<Transaction> = boxStore.boxFor(Transaction::class.java)
        val eventBox: Box<Event> = boxStore.boxFor(Event::class.java)
        val goalBox: Box<Goal> = boxStore.boxFor(Goal::class.java)
        val transactionCategoryBox: Box<TransactionCategory> = boxStore.boxFor(TransactionCategory::class.java)
        val eventCategoryBox: Box<EventCategory> = boxStore.boxFor(EventCategory::class.java)
    }

    object Repositories {
        val bankRepository =
            if (Config.DEBUG) BankInMemoryRepository() else BankBoxRepository(Database.bankBox)
        val transactionRepository =
            if (Config.DEBUG) TransactionInMemoryRepository() else TransactionBoxRepository(Database.transactionBox)
        val eventRepository =
            if (Config.DEBUG) EventInMemoryRepository() else EventBoxRepository(Database.eventBox)
        val goalRepository =
            if (Config.DEBUG) GoalInMemoryRepository() else GoalBoxRepository(Database.goalBox)
        val transactionCategoryRepository =
            if (Config.DEBUG) TransactionCategoryBoxRepository(Database.transactionCategoryBox) else TransactionCategoryBoxRepository(Database.transactionCategoryBox)
        val eventCategoryRepository = if(Config.DEBUG) EventCategoryBoxRepository(Database.eventCategoryBox) else EventCategoryBoxRepository(Database.eventCategoryBox)
    }

    object UseCases {
        val backup = Backup(
            bankRepository = Repositories.bankRepository,
            eventRepository = Repositories.eventRepository,
            transactionRepository = Repositories.transactionRepository,
            goalRepository = Repositories.goalRepository,
            transactionCategoryRepository = Repositories.transactionCategoryRepository,
            eventCategoryRepository = Repositories.eventCategoryRepository,
        ).attach(UI.notify)

        val createEvent = CreateEvent(Repositories.eventRepository)
            .attach(UI.cache)
            .attach(UI.notify)
        val getTransactionsByGoal = GetTransactionsByGoal(Repositories.transactionRepository)
        val getTransaction = GetTransaction(Repositories.transactionRepository)
        val createTransaction = CreateTransaction(Repositories.transactionRepository)
            .attach(UI.cache)
            .attach(UI.notify)
        val getAllTransactions =
            GetAllTransactions(Repositories.transactionRepository).attach(UI.cache)
        val updateTransaction =
            UpdateTransaction(Repositories.transactionRepository).attach(UI.cache)
        val deleteTransaction =
            DeleteTransaction(Repositories.transactionRepository).attach(UI.cache)
        val createGoal = CreateGoal(Repositories.goalRepository).attach(UI.cache)
        val getAllGoals = GetAllGoals(Repositories.goalRepository).attach(UI.cache)
        val getGoal = GetGoal(Repositories.goalRepository, Repositories.transactionRepository)
        val deleteGoal = DeleteGoal(
            Repositories.goalRepository,
            Repositories.transactionRepository
        ).attach(UI.cache)

        //        val getTransactionsByMonthAndYear =
//            GetTransactionsByMonthAndYear(Repositories.transactionRepository)
//        val getListOfTransactionsByMonthAndYear =
//            GetListOfTransactionsByMonthAndYear(Repositories.transactionRepository).attach(UI.cache)
        val createBank = CreateBank(Repositories.bankRepository)
            .attach(UI.notify).attach(UI.cache)
        val getAllBanks = GetAllBanks(Repositories.bankRepository).attach(UI.cache)
        val getWeeksData = GetWeeksData(
            transactionRepository = Repositories.transactionRepository,
            eventRepository = Repositories.eventRepository,
        ).attach(UI.cache)
        val getEventsByDate = GetEventsByDate(Repositories.eventRepository)
        val deleteEvent = DeleteEvent(Repositories.eventRepository).attach(UI.cache)
        val deleteBank = DeleteBank(
            Repositories.bankRepository,
            Repositories.transactionRepository
        ).attach(UI.cache)
        val getWeeksDataInFuture = GetWeekDataInFutureOrPast(
            transactionRepository = Repositories.transactionRepository,
            eventRepository = Repositories.eventRepository
        ).attach(UI.cache)

        val getBank = GetBank(Repositories.bankRepository, Repositories.transactionRepository)
        val getBankRaw = GetBankRaw(Repositories.bankRepository)
        val updateBank = UpdateBank(Repositories.bankRepository).attach(UI.cache)
        val createEventRecurrence = CreateRecurrenceEvent(Repositories.eventRepository)
        val createTransactionRecurrence =
            CreateRecurrenceTransaction(Repositories.transactionRepository)
    }
    @SuppressLint("StaticFieldLeak")
    object UI {
        lateinit var context: Context
        val pageRange = listOf(0, 1, 2)
        val cache = Cache()
        val notify = Notify()
        var title by mutableStateOf("…")
    }
}