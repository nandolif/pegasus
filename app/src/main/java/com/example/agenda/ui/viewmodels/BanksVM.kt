package com.example.agenda.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.agenda.app.App


class BanksVM : ViewModel() {
    val banks = App.UI.cache.getBanks()
}