package com.example.agenda.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BottomSheet
import com.example.agenda.ui.component.TXT

object Persons {
    object Default {
        object Mother {
            const val NAME_AND_ID = "Mãe"
        }

        object Father {
            const val NAME_AND_ID = "Pai"
        }
    }

    object Components {
        @Composable
        fun CreatePerson(): () -> Unit{
            val toggle = BottomSheet.Wrapper {
                TXT("Criar Pessoa")
            }
            return { toggle() }
        }
    }
}