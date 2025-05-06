typealias MONEY = Double

object Money {
    const val CURRENCY_SYMBOL = "R$"
    const val HUNDRED_DELIMITER = "."
    const val CENTS_DELIMITER = ","
    const val ZERO = 0.0

    data class Currency(
        val value: MONEY,
        val text: String,
    )


    fun convert(value: Any): MONEY? {
        if (value is MONEY) return value

        if (value is String) {
            return value.toDoubleOrNull() ?: ZERO
        }
        return null
    }


    fun isZero(value: Any): Boolean {
        val m = resolve(value, value)
        return m.value == ZERO

    }

    fun format(value: Any, withCurrency: Boolean = true): String {
        return if (withCurrency) CURRENCY_SYMBOL + resolve(value, value).text else resolve(
            value,
            value
        ).text
    }

    fun resolve(newValue: Any, oldValue: Any): Currency {
        var v1 = newValue.toString()
        var v2 = oldValue.toString()

        fun filter(value: String): String {
            return value.filter { it.isDigit() }
        }


        v1 = filter(v1)
        v2 = filter(v2)

        val number1 = v1.toInt()
        val number2 = v2.toInt()
        var value = 0.0

        if(number1 > number2) {
            value = (number1 * 10).toDouble() / 100
        } else {
            value = (number1 / 10).toDouble() / 100
        }

        var text = value.toString()

        val textSplit = text.split(".")
        val t = textSplit[0].mapIndexed({index, char -> {
            if(index % 3 == 0 && index != 0) {
                char+HUNDRED_DELIMITER
            } else {
                char
            }
        }}).joinToString("")

        text = t + CENTS_DELIMITER + textSplit[1]
        return Currency(
            value = value,
            text = text
        )
    }
}