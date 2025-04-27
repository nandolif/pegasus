package com.example.agenda.app.common.observer

import com.example.agenda.app.common.ObserverEvents
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface Subject<T> {
    val observers: MutableList<Observer>
    suspend fun notifyAll(event: ObserverEvents, value: Any) {
        for (observer in observers) {
            observer.update(
                event = event,
                value = value
            )
        }
    }

    fun attach(observer: Observer): T {
        observers.add(observer)
        return this as T
    }
}