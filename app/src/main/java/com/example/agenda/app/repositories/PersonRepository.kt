package com.example.agenda.app.repositories

import com.example.agenda.app.common.repositories.CRUDRepository
import com.example.agenda.domain.entities.Person
import com.example.agenda.ui.screens.Persons
import kotlinx.coroutines.runBlocking

interface PersonRepository: CRUDRepository<Person> {
    fun setup() {
        _setup(Persons.Default.Mother.NAME_AND_ID)
        _setup(Persons.Default.Father.NAME_AND_ID)
    }
    private fun _setup(id: String) {
        runBlocking {
            if (getById(id) == null) {
                create(
                    Person(
                        id = id, name = id,
                        created_at = null,
                        updated_at = null,
                        birthDay = null,
                        birthMonth = null,
                        birthYear = null,
                    )
                )
            }
        }
    }
}