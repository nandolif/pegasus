package com.example.agenda.domain.entities

import com.example.agenda.app.common.Entity
import io.objectbox.annotation.Entity as E
import io.objectbox.annotation.Id

@E
data class Bank(
    val name: String,
    var balance: Double,
    override var id: String?,
    @Id
    var _id: Long = 0,
    override var created_at: Long?,
    override var updated_at: Long?,
    val credit: Double?,
) : Entity {init {createMetadata()}}