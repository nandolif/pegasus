package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Entity as E

@E
data class Person(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override var created_at: Long?,
    override var updated_at: Long?,
    val name: String,
): Entity