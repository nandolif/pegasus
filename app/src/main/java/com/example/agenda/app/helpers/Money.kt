object Money {

    const val CURRENCY_SYMBOL = "R$"
    const val HUNDRED_DELIMITER = "."
    const val CENTS_DELIMITER = ","

    data class Currency(
        val float: Float,
        val string: String,
    )

    fun isZero(value: Any): Boolean {
        val m = resolve(value,value)
        return m.float == 0f

    }

    fun format(value: Any, withCurrency: Boolean = true): String {
        return if(withCurrency) CURRENCY_SYMBOL + resolve(value,value).string else resolve(value,value).string
    }
    fun resolve(newValue: Any, oldValue: Any): Currency {
        fun sanitizePrice(value: Any): String {
            var prevalue = value
            if (value is Float) {
                prevalue = "%.2f".format(value)
            }
            prevalue = prevalue.toString()

            var newVal =
                prevalue.replace(" ", "").replace(CURRENCY_SYMBOL, "")
                    .replace(HUNDRED_DELIMITER, "")

            if (newVal.isEmpty()) {
                newVal = "0${CENTS_DELIMITER}00"
                return newVal
            }

            if (!newVal.contains(CENTS_DELIMITER)) {
                val w = newVal.split("").filter { it.isNotEmpty() }.toMutableList()

                w.add(w.size - 2, CENTS_DELIMITER)
                newVal = w.joinToString("")
            }
            val newValSplitted = newVal.split(CENTS_DELIMITER).toMutableList()

            newValSplitted[0] = newValSplitted[0].filter { it.isDigit() }
            newValSplitted[1] = newValSplitted[1].filter { it.isDigit() }

            if (newValSplitted[0].isEmpty()) {
                newValSplitted[0] = "0"
            }
            if (newValSplitted[1].isEmpty()) {
                newValSplitted[1] = "00"
            }


            return "${newValSplitted[0]}$CENTS_DELIMITER${newValSplitted[1]}"
        }


        fun centsSanitizer(new: String, old: String): String {
            val splittedValue = new.split(CENTS_DELIMITER).toMutableList()
            val originalSplittedValue = old.split(CENTS_DELIMITER).toMutableList()

            if (originalSplittedValue[1].length > 2 || splittedValue[1].length > 3) {
                splittedValue[1] = "00"
                originalSplittedValue[1] = "00"
            }

            if (splittedValue[1].length > 2) {
                val splittedValueCents = splittedValue[1].split("").filter { it.isNotEmpty() }
                val originalCents =
                    originalSplittedValue[1].split("").filter { it.isNotEmpty() }.toMutableList()

                if (originalCents[0] != splittedValueCents[0]) {
                    originalCents[0] = splittedValueCents[0]
                } else if (originalCents[1] != splittedValueCents[1]) {
                    originalCents[1] = splittedValueCents[1]
                } else if (originalCents[1] != splittedValueCents[2]) {
                    originalCents[1] = splittedValueCents[2]
                }
                splittedValue[1] = originalCents.joinToString("")
            }

            if (splittedValue[1].length < 2) {
                splittedValue[1] = "${splittedValue[1][0]}0"
            }

            return splittedValue[1]
        }


        fun hundredsSanitizer(new: String, old: String): String {
            val newSplitted = new.split(CENTS_DELIMITER).toMutableList()
            val oldSplitted = old.split(CENTS_DELIMITER).toMutableList()
            val hundredsSplitted = newSplitted[0].split("").filter { it.isNotEmpty() }
            val originalSplitted =
                oldSplitted[0].split("").filter { it.isNotEmpty() }.toMutableList()

            if (newSplitted[0].length > 1 && oldSplitted[0].length == 1) {
                if (originalSplitted[0] == "0") {
                    originalSplitted[0] = hundredsSplitted.filter { it != "0" }.joinToString("")
                    newSplitted[0] = originalSplitted.joinToString("")
                }

                if (originalSplitted[0].isEmpty()) {
                    newSplitted[0] = "0"
                }
            }
            if (newSplitted[0].length > 6 && oldSplitted[0].length == 6) {
                if (originalSplitted[0] != hundredsSplitted[0]) {
                    originalSplitted[0] = hundredsSplitted[0]
                }
                newSplitted[0] = originalSplitted.joinToString("")
            }


            if (newSplitted[0].length > 3) {
                val numbers = newSplitted[0].split("").filter { it.isNotEmpty() }.toMutableList()
                numbers.add(numbers.size - 3, HUNDRED_DELIMITER)
                newSplitted[0] = numbers.joinToString("")
            }


            return newSplitted[0]
        }

        fun exec(): Currency {
            val n = sanitizePrice(newValue)
            val o = sanitizePrice(oldValue)
            val cents = centsSanitizer(n, o)
            val hundreds = hundredsSanitizer(n, o)

            return Currency(
                string = "$hundreds$CENTS_DELIMITER$cents",
                float = "${hundreds.filter { it.isDigit() }}.${cents}f".toFloat()
            )
        }
        return exec()
    }
}