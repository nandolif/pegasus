package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Bank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking


class BanksVM : ViewModel() {
    val banks = MutableStateFlow(mutableListOf<Bank>())


    fun fetchData() {
        runBlocking {
            banks.value = App.UseCases.getAllBanks.execute(Unit).toMutableList()
        }
    }
    init {
        fetchData()
    }
}