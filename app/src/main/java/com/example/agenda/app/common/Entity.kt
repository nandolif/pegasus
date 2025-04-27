package com.example.agenda.app.common

import com.example.agenda.app.App
import com.example.agenda.domain.valueObjects.Id

interface Entity {
    var id: String?
    var created_at: Long?
    var updated_at: Long?
    fun createMetadata() {
        if (id == null) {
            id = Id.create()
        }
        if (created_at == null && updated_at == null) {
            val now = App.Time.now()
            created_at = now
            updated_at = now
        }
    }
}