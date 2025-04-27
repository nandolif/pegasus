package com.example.agenda.app.common.observer

import com.example.agenda.app.common.ObserverEvents

interface Observer {
    suspend fun update(event: ObserverEvents, value: Any)
}