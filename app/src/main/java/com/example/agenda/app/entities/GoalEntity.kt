package com.example.agenda.app.entities

import com.example.agenda.app.common.Entity

interface GoalEntity: Entity {
    val achieved: Boolean
    val amount: Float
    val actualAmount: Float?
    val title: String
}