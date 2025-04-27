package com.example.agenda.ui.viewmodels

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.agenda.ui.system.Navigation
import kotlinx.coroutines.flow.MutableStateFlow

class StructureVM: ViewModel() {
    var buttonsVisible = MutableStateFlow(false)
    val fadeInOutMillis = 300
    val distanceFloatingButtons = 15.dp

    fun toggleButtons() {
        buttonsVisible.value = !buttonsVisible.value
    }
}