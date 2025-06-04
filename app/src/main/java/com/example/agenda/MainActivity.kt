package com.example.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.agenda.app.App
import com.example.agenda.ui.component.IconPicker
import com.example.agenda.ui.system.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            App.UI.context = this
            App.UI.notify.NotifyComponent()
            Navigation.Host()
            LaunchedEffect(Unit) {
                App.Repositories.transactionCategoryRepository.setup()
                App.Repositories.eventCategoryRepository.setup()
                App.Repositories.bankRepository.setup()
                App.Repositories.personRepository.setup()
                App.Repositories.goalRepository.setup()
            }
        }
    }
}

