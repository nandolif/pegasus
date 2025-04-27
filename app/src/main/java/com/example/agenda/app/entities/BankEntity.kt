package com.example.agenda.app.entities

import com.example.agenda.app.common.Entity

interface BankEntity: Entity {
    val balance: Float
    val name: String
}