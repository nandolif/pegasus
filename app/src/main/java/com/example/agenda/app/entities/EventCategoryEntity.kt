package com.example.agenda.app.entities

import com.example.agenda.app.common.ColorEntity
import com.example.agenda.app.common.Entity

interface EventCategoryEntity: Entity, ColorEntity {
    val name: String
}