package com.example.agenda.domain.entities

import androidx.compose.runtime.mutableStateOf
import com.example.agenda.app.App
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.app.entities.TransactionCategoryEntity
import com.example.agenda.app.entities.TransactionEntity
import com.example.agenda.domain.repositories.box.RecurrenceConverter
import com.example.agenda.ui.screens.TransactionCategories
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

@Entity
data class Transaction(
    @Id
    var _id: Long = 0,
    override var id: String?,
    override val day: Int,
    override val month: Int,
    override val year: Int,
    override val amount: Float,
    override val description: String,
    override var created_at: Long?,
    override var updated_at: Long?,
    override val bankId: String,
    override val recurrenceId: String?,
    override val ghost: Boolean,
    override val goalId: String?,
    override val canceled: Boolean,
    override val canceledDay: Int?,
    override val canceledMonth: Int?,
    override val canceledYear: Int?,
    override val nDays: Int?,
    override val nWeeks: Int?,
    override val nMonths: Int?,
    override val nYears: Int?,
    @Convert(converter = RecurrenceConverter::class, dbType = String::class)
    override val recurrenceType: RECURRENCE?,
    override val categoryId: String,
) : TransactionEntity {
    init {
        createMetadata()
//        categoryId = this.setGoalCategoryIfNeeded()
    }
}

//fun Transaction.setGoalCategoryIfNeeded(): String {
//    if (goalId == null) return categoryId
//    val category = mutableStateOf<TransactionCategoryEntity?>(null)
//    runBlocking {
//        category.value =
//            App.Repositories.transactionCategoryRepository.getByName(TransactionCategories.GOAL_CATEGORY_NAME)
//    }
//
//    if (category.value == null) {
//        runBlocking {
//            val cat = TransactionCategory(
//                id = null,
//                name = TransactionCategories.GOAL_CATEGORY_NAME,
//                created_at = null,
//                updated_at = null,
//            )
//
//            App.Repositories.transactionCategoryRepository.create(cat)
//            category.value = cat
//
//        }
//    }
//    return category.value!!.id!!
//}
