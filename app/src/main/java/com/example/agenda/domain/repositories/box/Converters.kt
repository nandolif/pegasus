package com.example.agenda.domain.repositories.box

import com.example.agenda.app.common.EventType
import com.example.agenda.app.common.RECURRENCE
import com.example.agenda.ui.screens.Transactions
import io.objectbox.converter.PropertyConverter

class RecurrenceConverter(): PropertyConverter<RECURRENCE?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): RECURRENCE? {
        return when (databaseValue) {
            RECURRENCE.EVERY_N_DAYS.name -> RECURRENCE.EVERY_N_DAYS
            RECURRENCE.EVERY_N_WEAK.name -> RECURRENCE.EVERY_N_WEAK
            RECURRENCE.EVERY_N_MONTH_LAST_DAY.name -> RECURRENCE.EVERY_N_MONTH_LAST_DAY
            RECURRENCE.EVERY_N_YEARS.name -> RECURRENCE.EVERY_N_YEARS
            else -> null
        }
    }

    override fun convertToDatabaseValue(entityProperty: RECURRENCE?): String? {
        if (entityProperty != null) {
           return entityProperty.name
        }
        return null
    }
}

class EventTypeConverter(): PropertyConverter<EventType?, String?>{
    override fun convertToEntityProperty(databaseValue: String?): EventType? {
        return when (databaseValue) {
            EventType.BIRTHDAY.name -> EventType.BIRTHDAY
            EventType.IMPORTANT.name -> EventType.IMPORTANT
            EventType.HOLIDAY.name -> EventType.HOLIDAY
            EventType.REMINDER.name -> EventType.REMINDER
            EventType.OTHER.name -> EventType.OTHER
            else -> null
        }
    }

    override fun convertToDatabaseValue(entityProperty: EventType?): String? {
        return entityProperty?.name
    }

}

class TransactionTypeConverter: PropertyConverter<Transactions.Type, String>{
    override fun convertToEntityProperty(databaseValue: String?): Transactions.Type {
        return when (databaseValue) {
            Transactions.Type.INCOME.value -> Transactions.Type.INCOME
            Transactions.Type.EXPENSE.value -> Transactions.Type.EXPENSE
            Transactions.Type.TRANSFER.value -> Transactions.Type.TRANSFER
            else -> Transactions.Type.EXPENSE
        }
    }

    override fun convertToDatabaseValue(entityProperty: Transactions.Type): String {
        return entityProperty.value
    }

}