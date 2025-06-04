package com.example.agenda.ui.component.transactions.createBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.agenda.ui.component.Form.Row
import com.example.agenda.ui.component.TXT


data class Screen(val isAvailable: Boolean = true, val content: @Composable () -> Unit)

@Composable
fun Test(screens: MutableMap<String, Screen>, screenName: String) {
    fun balls(): @Composable () -> Unit {
        return {
            LazyRow {
                items(screens.size) {
                    TXT(it.toString())
                }
            }
        }
    }
    val screen = screens[screenName]!!
    Column {
        balls()()
        screen.content()
    }
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    var screenName by remember { mutableStateOf("Sexo") }
    val screens by remember { mutableStateOf<MutableMap<String, Screen>>(mutableMapOf()) }
    fun removeSexoScreens() {
        screens.remove("Sexo4")
    }

    screens["Sexo"] = Screen(true, {
        TXT("Sexo")
        Button(onClick = { screenName = "Sexo1" }) {
            TXT("Sexo1")
        }
    })
    screens["Sexo1"] = Screen(true, {
        TXT("Sexo1")
        Button(onClick = { screenName = "Sexo" }) {
            TXT("Sexo")
            removeSexoScreens()
        }
    })
    screens["Sexo3"] = Screen(true, { TXT("Sexo3") })
    screens["Sexo4"] = Screen(true, { TXT("Sexo4") })

    Test(screens, screenName)
}