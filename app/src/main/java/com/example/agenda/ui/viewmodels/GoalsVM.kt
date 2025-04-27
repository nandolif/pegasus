package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App

class GoalsVM() : ViewModel() {
    var goals = App.UI.cache.getGoals()
}
