package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App
import com.example.agenda.domain.entities.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class GoalsVM() : ViewModel() {
    var goals = MutableStateFlow<List<Goal>>(listOf())

    fun fetchData() {
        runBlocking {
            goals.value = App.Repositories.goalRepository.getAll()
        }
    }

    init {
        fetchData()
    }
}
