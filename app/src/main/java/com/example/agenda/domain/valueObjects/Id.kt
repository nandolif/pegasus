package com.example.agenda.domain.valueObjects

import com.example.agenda.app.common.ValueObject
import java.util.UUID

class Id(
    val value: String,
): ValueObject {
    companion object {
        fun create(): String {
            return UUID.randomUUID().toString()
        }
    }

    override fun isValid(): Boolean {
        return true
    }
}
