package com.example.agenda.ui.system

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.agenda.app.App
import com.example.agenda.app.common.ObserverEvents
import com.example.agenda.app.common.observer.Observer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Notify() : Observer {
    private val messages = MutableStateFlow<List<String>>(emptyList())

    val CHANNEL_ID = "a"
    val CHANNEL_NAME = "aa"


    @Composable
    fun NotifyComponent() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(App.UI.context, NotificationManager::class.java)
        manager?.createNotificationChannel(channel)

        val m by messages.collectAsState()
        LaunchedEffect(m) {
            launch {
                for (message in m) {
                    Toast.makeText(App.UI.context, message, Toast.LENGTH_SHORT).show()
                }
                messages.value = emptyList()
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun displayNotification(title: String, content: String) {
        val mBuilder = NotificationCompat.Builder(App.UI.context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_menu_myplaces)

        val mNotificationManager = NotificationManagerCompat.from(App.UI.context)
        mNotificationManager.notify(1, mBuilder.build())
    }

    fun add(message: String): Unit? {
        messages.value = messages.value.plus(message)
        println(message)
        return null
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun update(event: ObserverEvents, value: Any) {
        when (event) {
            ObserverEvents.CREATE_BANK -> {
                val message = "Banco criado com sucesso"
                messages.value = messages.value.plus(message)
            }

            ObserverEvents.CREATE_TRANSACTION -> {
                val message = "Transação criada com sucesso"
                messages.value = messages.value.plus(message)

            }

            ObserverEvents.CREATE_EVENT -> {
                val message = "Evento criado com sucesso"
                messages.value = messages.value.plus(message)

            }

            ObserverEvents.BACKUP -> {
                val message = "Backup realizado com sucesso"
                messages.value = messages.value.plus(message)
            }

            else -> {}
        }
    }
}