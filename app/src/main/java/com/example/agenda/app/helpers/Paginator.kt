package com.example.agenda.app.helpers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.ceil

object Paginator {

    class Data<T>(
        val pages: Int,
        val actualPage: MutableState<Int>,
        val data: Map<Int, List<T>>,
    ) {
        fun next() {
            if (actualPage.value < pages) {
                actualPage.value += 1
            }
        }

        fun previous() {
            if (actualPage.value > 1) {
                actualPage.value -= 1
            }
        }

        fun go(page: Int) {
            actualPage.value = page
        }

        fun buttons(quantity: Int = 5): List<Int> {
            val list = mutableListOf<Int>()
            val half = (quantity.toFloat() / 2).toInt()
            val actual = actualPage.value

            for (i in 1..half) {
                val a = actual - i
                if (a == 0) break
                list.add(a)
            }

            var index = quantity - list.size - 1
            for (i in 1..index) {
                val a = actual + i
                if (a -1 >= pages) break
                list.add(a)
            }


            if (list.size != quantity - 1) {
                val i = quantity-list.size -1
                val first = list.sorted()[0]
                for (v in 1..i) {
                    val a = first - v
                    if(a == 0) break
                    list.add(a)
                    if(list.size >= quantity) break
                }
            }

            list.add(actual)
            return list.sorted()
        }
    }


    fun <T> get(list: List<T>, perPage: Int): Data<T> {
        val pages = ceil(list.size.toDouble() / perPage.toDouble()).toInt()
        val data: MutableMap<Int, List<T>> = mutableMapOf()
        var start = 0
        var end = perPage - 1

        for (i in 1..pages) {
            if (end > list.size - 1) end = list.size - 1
            val subList = list.subList(start, end)
            data[i] = subList
            start += perPage
            end += perPage
        }

        return Data(
            pages = pages,
            actualPage = mutableStateOf(1),
            data = data,
        )
    }


}