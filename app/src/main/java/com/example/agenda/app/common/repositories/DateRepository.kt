package com.example.agenda.app.common.repositories

import com.example.agenda.app.objects.DayMonthYearObject

interface DateRepository<T> {
    suspend fun getByDate(date: DayMonthYearObject): List<T>
    suspend fun getNByDate(date: DayMonthYearObject, quantity: Int = 3): List<T>
    suspend fun getByMonthAndYear(date: DayMonthYearObject): List<T>
}