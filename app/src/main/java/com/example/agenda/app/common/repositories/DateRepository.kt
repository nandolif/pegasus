package com.example.agenda.app.common.repositories

import com.example.agenda.domain.objects.DayMonthYearObj


interface DateRepository<T> {
    suspend fun getByDate(date: DayMonthYearObj): List<T>
    suspend fun getNByDate(date: DayMonthYearObj, quantity: Int = 3): List<T>
    suspend fun getByMonthAndYear(date: DayMonthYearObj): List<T>
}