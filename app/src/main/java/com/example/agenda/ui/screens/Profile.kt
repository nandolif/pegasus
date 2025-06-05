package com.example.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.agenda.app.App
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.Structure
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

object Profile {
    @Serializable
    class Route

    @Composable
    fun Screen() {
        Structure.Wrapper(
            header = { Structure.Header("Perfil") },
            bottom = { Structure.BottomBar() }) {
            Column {
                BTN("Criar Backup", onClick = {
                    runBlocking {
                        App.UseCases.backup.execute(Unit)
                    }
                })
                BTN("Restaurar Backup", onClick = {
                    runBlocking {
                        App.UseCases.backup.restore()
                    }
                })
            }
        }
    }

}