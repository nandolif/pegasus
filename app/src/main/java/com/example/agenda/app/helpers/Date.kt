package com.example.agenda.app.helps

import com.example.agenda.app.App
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.domain.objects.DateObj
import com.example.agenda.domain.objects.DayMonthYearObj
import com.example.agenda.domain.objects.WeekObj
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.properties.Delegates


object Date {

    fun between(start: DayMonthYearObj, end: DayMonthYearObj): String {
        if(start.day == end.day && start.month == end.month && start.year == end.year){
            return "Hoje"
        }
        val difference = difference(start, end)
        if(difference < 7){
            return if(difference != 1) "$difference dias" else "$difference dia"
        }

        if(difference < 30){
            val date = (difference/7)
            return if(date != 1) "${date} semanas" else "${date} semana"
        }

        if(difference < 365){
            val date = (difference/30)
            return if(date != 1) "${date} meses" else "${date} mês"
        }

        val date = (difference/365)
        return if(date != 1) "${date} anos" else "${date} ano"
    }


    fun difference(start: DayMonthYearObj, end: DayMonthYearObj): Int {
        val startDate = LocalDate.of(start.year, start.month, start.day)
        val endDate   = LocalDate.of(end.year,   end.month,   end.day)
        return ChronoUnit.DAYS.between(startDate, endDate).toInt()
    }

    fun longToDayMonthYear(
        timestamp: Long,
    ): DayMonthYearObj {
        val i = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC).toLocalDate()
            .format(DateTimeFormatter.ofPattern(App.Time.PATTERN)).toString()

        val list = i.split("/")
        val day = list[0].toInt()
        val month = list[1].toInt()
        val year = list[2].toInt()
        return DayMonthYearObj(day, month, year)
    }

    fun stringToDayMonthYear(
        date: String
    ): DayMonthYearObj {
        val list = date.split("/")
        val day = list[0].toInt()
        val month = list[1].toInt()
        val year = list[2].toInt()
        return DayMonthYearObj(day, month, year)
    }

    fun dayMonthYearToString(
        date: DayMonthYearObj
    ): String {
       return if(date.day < 10) {"0" + date.day.toString() } else {date.day.toString()} + "/" +
               if(date.month < 10){ "0"+ date.month.toString()} else {date.month.toString()} +
               "/" + date.year.toString()
    }

    fun getFirstSundayNextToTheFirstDayOfTheMonth(date: DayMonthYearObj): DayMonthYearObj {
        var firstSunday = DayMonthYearObj(day= 1, month = date.month, year = date.year)
        val todayNumber = getWeekTextToDay(firstSunday)
        firstSunday = DayMonthYearObj(-todayNumber, firstSunday.month, firstSunday.year)
        return getDate(firstSunday)
    }

    fun getWeeks(
        date: DayMonthYearObj,
        totalWeeks: Int,
        firstDayOfWeek: Int = 1,
    ): List<WeekObj> {
        val firstSunday = getFirstSundayNextToTheFirstDayOfTheMonth(date)
        var day = DayMonthYearObj(day = firstSunday.day + (firstDayOfWeek - 1), firstSunday.month, firstSunday.year)
        val weeks = mutableListOf<WeekObj>()
        for (i in 1..totalWeeks) {
            val list = mutableListOf<DateObj>()
            for (j in 1..7) {
                val a = DayMonthYearObj(
                    day.day + 1,
                    day.month,
                    day.year
                )
                day = getDate(a)


                val dateObj = DateObj(
                    date = day,
                    transactions = listOf(),
                    events = listOf()
                )
                list.add(dateObj)
            }
            weeks.add(
                WeekObj(
                    days = list
                )
            )
        }

        return weeks.toList()
    }

    fun getWeekTextToDay(date: DayMonthYearObj): Int {
        val dateText = getDayOfWeek(date)
        return when (dateText) {
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            "Sunday" -> 7
            else -> 0
        }


    }

    fun geMonthText(date: DayMonthYearObj): String {
        return when (date.month) {
            1 -> "Janeiro"
            2 -> "Fevereiro"
            3 -> "Março"
            4 -> "Abril"
            5 -> "Maio"
            6 -> "Junho"
            7 -> "Julho"
            8 -> "Agosto"
            9 -> "Setembro"
            10 -> "Outubro"
            11 -> "Novembro"
            12 -> "Dezembro"
            else -> ""
        }
    }

    fun getDayOfWeek(date: DayMonthYearObj): String {
        // Create a LocalDate instance with given day, month, and year.
        val d = LocalDate.of(date.year, date.month, date.day)
        // Get the full textual name of the day of the week (e.g., "Monday").
        return d.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    }

    fun getTodayTimestamp(): Long {
        val today = getToday()
        return Instant.ofEpochSecond(0)
            .atZone(ZoneId.of(App.Time.ZONE_ID))
            .withDayOfMonth(today.day)
            .withMonth(today.month)
            .withYear(today.year).toEpochSecond()
    }

    fun getToday(): DayMonthYearObj {
        val today = Instant.now().atZone(ZoneId.of(App.Time.ZONE_ID))
        return DayMonthYearObj(
            day = today.dayOfMonth,
            month = today.monthValue,
            year = today.year
        )
    }

    fun getAllDaysByMonthAndYear(month: Int, year: Int): List<Int> {
        var day by Delegates.notNull<Int>()
        val list = mutableListOf<Int>()
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> {
                day = 31
            }

            4, 6, 9, 11 -> {
                day = 30
            }

            2 -> {
                if (year % 4 == 0) {
                    day = 29
                } else {
                    day = 28
                }
            }
        }
        for (i in 1..day) {
            list.add(i)
        }
        return list
    }

    private fun monthDifference(month: Int, year: Int): DayMonthYearObj {
        var m = month
        var y = year
        if (m > 12) {
            m -= 12
            y += 1
        }
        if (m < 1) {
            m += 12
            y -= 1
        }

        if (month > 12) {
            monthDifference(m, y)
        }

        return DayMonthYearObj(0, m, y)
    }

    private fun dayDifference(day: Int, month: Int, year: Int): DayMonthYearObj {
        var d = day
        var m = month
        var y = year
        var allDays = getAllDaysByMonthAndYear(m, y)

        if (!allDays.contains(d)) {
            if (d < 1) {
                m -= 1
                val monthObj = monthDifference(m, y)
                val allDays2 = getAllDaysByMonthAndYear(monthObj.month, monthObj.year)
                d = if (d == 0) {
                    allDays2.last()
                } else {
                    allDays2.last() - (abs(d) - 1)
                }
                m = monthObj.month
                y = monthObj.year
            } else {
                d -= allDays.last()
                m += 1
                val monthObj = monthDifference(m, y)
                m = monthObj.month
                y = monthObj.year
            }
        } else {
            return DayMonthYearObj(d, m, y)
        }
        allDays = getAllDaysByMonthAndYear(m, y)

        if (!allDays.contains(d)) {
            dayDifference(d, m, y)
        }

        return DayMonthYearObj(d, m, y)
    }

    fun getDate(date: DayMonthYearObj): DayMonthYearObj {
        var day = date.day
        var month = date.month
        var year = date.year

        val monthObj = monthDifference(month, year)

        month = monthObj.month
        year = monthObj.year

        val dayObj = dayDifference(day, month, year)

        day = dayObj.day
        month = dayObj.month
        year = dayObj.year


        return DayMonthYearObj(day, month, year)
    }

    fun getNextRecurrence(
        date: DayMonthYearObj,
        type: RECURRENCE,
        nDays: Int?,
        nWeeks: Int?,
        nMonths: Int?,
        nYears: Int?,
    ): DayMonthYearObj {
        return when (type) {
            RECURRENCE.EVERY_N_DAYS -> {
                getDate(DayMonthYearObj(date.day + nDays!!, date.month, date.year))
            }

            RECURRENCE.EVERY_N_WEAK -> {
                getDate(DayMonthYearObj(date.day + 7 * nWeeks!!, date.month, date.year))
            }

            RECURRENCE.EVERY_N_MONTH_LAST_DAY -> {
                getDate(DayMonthYearObj(date.day, date.month + nMonths!!, date.year))
            }

            RECURRENCE.EVERY_N_YEARS -> {
                val obj = getDate(DayMonthYearObj(date.day, date.month, date.year + nYears!!))
                val days = getAllDaysByMonthAndYear(obj.month, obj.year)
                //val day = days.last()
                getDate(DayMonthYearObj(obj.day, obj.month, obj.year))
            }
        }
    }

    fun isInFuture(date: DayMonthYearObj, today: DayMonthYearObj): Boolean {
        if (date.year > today.year) {
            return true
        }
        if (date.year == today.year && date.month > today.month) {
            return true
        }
        if (date.year == today.year && date.month == today.month && date.day > today.day) {
            return true
        }
        return false
    }
}
