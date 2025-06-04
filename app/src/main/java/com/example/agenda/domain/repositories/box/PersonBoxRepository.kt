package com.example.agenda.domain.repositories.box

import com.example.agenda.app.App
import com.example.agenda.app.repositories.PersonRepository
import com.example.agenda.domain.entities.Person
import io.objectbox.Box

class PersonBoxRepository(val box: Box<Person>):PersonRepository {
    override suspend fun create(entity: Person) {
        box.put(entity)
    }

    override suspend fun update(entity: Person) {
        box.all.find { it.id == entity.id }?.let {
            box.put(
                it.copy(
                    name = entity.name,
                    updated_at = App.Time.now()
                )
            )
        }
    }

    override suspend fun delete(entity: Person): Boolean {
        box.all.find { it.id == entity.id }?.let {
            box.remove(it)
        }
        return true
    }

    override suspend fun getAll(): List<Person> {
        return box.all
    }

    override suspend fun getById(id: String): Person? {
        if(box.all.isEmpty()) return null
        return box.all.firstOrNull() { it.id == id }
    }
}