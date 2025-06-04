import com.example.agenda.app.helps.Calculator
import java.math.BigDecimal
import java.math.RoundingMode

object Money {
    const val CURRENCY_SYMBOL = "R$"
    const val HUNDRED_DELIMITER = "."
    const val CENTS_DELIMITER = ","
    const val ZERO = 0.0

    data class Currency(
        val value: Double,
        val text: String,
    )





    fun resolve(value: Any, withCurrency: Boolean = false, withSign: Boolean = true): Currency {
        var v = value
        if(v is Double) v = Calculator.round(v)
        if (v is Double && v.toString().split(".")[1].length == 1) {
            v = v.toString() + "0"
        }
        var finalValue = v.toString().filter { it.isDigit() }.toIntOrNull()?.toDouble() ?: ZERO
        finalValue /= 100
        var text = if (finalValue == ZERO) "0.00" else finalValue.toString()
        val valueSplit = text.split(".")
        val hundreds = valueSplit[0].reversed().mapIndexed { index, c ->
            if (index % 3 == 0 && index != 0) {
                c + HUNDRED_DELIMITER
            } else c
        }.reversed().joinToString("")
        val cents = if (valueSplit[1].length <= 1) valueSplit[1] + "0" else valueSplit[1]

        text = hundreds + CENTS_DELIMITER + cents

        text = if(v.toString()[0] == '-' && withSign) "-$text" else text

        return Currency(
            value = finalValue,
            text = if (withCurrency) CURRENCY_SYMBOL + text else text
        )
    }

    enum class CombineType {
        SumOrSub,
        MultiOrDivide
    }

    fun combine(type: CombineType, or: Boolean = true, vararg args: Double): Currency {
        val result = when (type) {
            CombineType.SumOrSub -> args.reduce { acc, d -> if (or) acc + d else acc - d }
            CombineType.MultiOrDivide -> args.reduce { acc, d -> if (or) acc * d else acc / d }
        }
        return Currency(
            value = Calculator.round(result),
            text = "%.2f".format(result)
        )
    }
}