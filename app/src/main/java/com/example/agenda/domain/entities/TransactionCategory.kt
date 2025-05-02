package com.example.agenda.domain.entities

import androidx.compose.ui.graphics.Color
import com.example.agenda.app.entities.TransactionCategoryEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TransactionCategory(
    @Id
    var _id : Long = 0,
    override var id: String?,
    override val name: String,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val textColor: String,
    override val backgroundColor: String
): TransactionCategoryEntity {init{createMetadata()}}