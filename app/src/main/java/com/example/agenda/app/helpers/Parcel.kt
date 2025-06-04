package com.example.agenda.app.helpers

import com.example.agenda.app.helps.Calculator

object Parcel {


    enum class Recurrence(val type: String) {
        Monthly("Mensal"),
        SemiMonthly("Semimensal"),
        BiMonthly("Bimestral"),
        TriMonthly("Trimestral"),
        QadriMonthly("Quadrimestral"),
    }


    data class Data(
        val fees: Money.Currency = Money.resolve(Money.ZERO),
        val quantity: Int = 1,
        val total: Money.Currency = Money.resolve(Money.ZERO),
        val recurrence: Recurrence = Recurrence.Monthly
    )

    fun total(amount: Double, feesPercentage: Double, quantity: Int): Double {
        val fees = amount * feesPercentage / 100
        val totalFees = fees * quantity
        val result = amount + totalFees - fees
        return Calculator.round(result)
    }

    fun amount(total: Double, quantity: Int): Double {
        return Calculator.round(total / quantity)
    }
}