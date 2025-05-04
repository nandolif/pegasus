package com.example.agenda.app.helps

import com.example.agenda.app.common.Entity
import com.example.agenda.domain.entities.Variable

object Calculator {
    fun eval(value: String, variables: List<Variable>): Float {
        var newValue = substituteVariable(value, variables)
        newValue = separateEquations(newValue)
        return newValue.toFloat()
    }
    fun getLocalVariables(c: Entity): List<Variable> {
        val variables = mutableListOf<Variable>()
        var t = c.toString()
        t = t.replace(",","")
        t = t.replace(" ", "=")
        val v = t.split("=").toMutableList()
        var m = v[0].split("(")[1]
        v[0] = m
        m = v[v.lastIndex].split(")")[0]
        v[v.lastIndex] = m

        for (va in v) {
            if (v.indexOf(va) % 2 == 1) {
                continue
            }
            variables.add(
                Variable(
                    name = v[v.indexOf(va)],
                    value = v[v.indexOf(va)+1],
                    id = null,
                    created_at = null,
                    updated_at = null
                )
            )
        }
        return variables
    }
    private fun substituteVariable(value: String, variables: List<Variable>): String {
        var t = value
        for (variable in variables) {
            if (t.contains("{${variable.name}}")) {
                t = t.replace("{${variable.name}}", variable.value)
            }
        }

        if (t.contains("{")) {
            return substituteVariable(t, variables)
        }
        return t
    }
    private fun separateEquations(value: String): String {
        var t = value
        if (t.contains("(")) {
            var parenthesisOpenIndex: Int?
            var parenthesisCloseIndex: Int? = null
            parenthesisOpenIndex = t.indexOfLast { it == '(' }
            for (i in parenthesisOpenIndex until t.length) {
                if (t[i] == ')') {
                    parenthesisCloseIndex = i
                    break
                }
            }
            if (parenthesisCloseIndex != null) {
                var content = value.substring(parenthesisOpenIndex + 1, parenthesisCloseIndex)

                content = calculate(content)
                t = t.replaceRange(parenthesisOpenIndex, parenthesisCloseIndex + 1, content)
            }
            if (t.contains("(")) {
                return separateEquations(t)
            }
        }
        return calculate(t)

    }
    private fun calculate(value: String): String {
        var t = value
        var newT = t
        var indexOperator: Int? = null
        var nextIndexOperator: Int? = null
        for (v in t) {
            if (v == '*' || v == '/' || v == '-' || v == '+') {
                indexOperator = t.indexOf(v)
                break
            }
        }

        if (indexOperator != null) {
            for (i in indexOperator + 1 until t.length) {
                if (t[i] == '*' || t[i] == '/' || t[i] == '-' || t[i] == '+') {
                    nextIndexOperator = i
                    break
                }
            }
            if (nextIndexOperator != null) {
                newT = t.split(t[nextIndexOperator])[0]
                val newT = newT.split(newT[indexOperator]).map { it.toFloat() }.reduce { acc, fl ->
                    when (newT[indexOperator]) {
                        '*' -> acc * fl
                        '/' -> acc / fl
                        '-' -> acc - fl
                        else -> acc + fl
                    }
                }.toString()
                t = "${newT} ${t[nextIndexOperator]} ${t.split(t[nextIndexOperator])[1]}"
            } else {
                t = t.split(t[indexOperator]).map { it.toFloat() }.reduce { acc, fl ->
                    when (t[indexOperator]) {
                        '*' -> acc * fl
                        '/' -> acc / fl
                        '-' -> acc - fl
                        else -> acc + fl
                    }
                }.toString()
            }
        }


        if (t.contains("*") || t.contains("/") || t.contains("-") || t.contains("+")) {
            return calculate(t)
        }
        return t
    }
}
