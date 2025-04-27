package com.example.agenda.domain.entities

import com.example.agenda.app.entities.BankEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Bank(

    override val name: String,
    override var balance: Float,
    override var id: String?,
    @Id
    var _id: Long = 0,
    override var created_at: Long?,
    override var updated_at: Long?,
) : BankEntity {
    init{createMetadata();}
}