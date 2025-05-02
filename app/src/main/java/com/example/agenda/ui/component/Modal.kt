package com.example.agenda.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme

object Modal {
    @Composable
    fun Component(
        title: String,
        buttons: @Composable() (() -> Unit)? = null,
        content: @Composable () -> Unit,
    ) {
        AlertDialog(
            containerColor = Theme.Colors.A.color,
            title = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TXT(s = title, fs = 20)
                }
            },
            text = {
                content()
            },
            onDismissRequest = {
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }

    @Composable
    fun Button(text: String, width: Float, onClick: () -> Unit, type: BTNType = BTNType.PRIMARY) {
        BTN(onClick = onClick, modifier = Modifier.fillMaxWidth(width), type = type, text = text)
    }
    @Composable
    fun Options(
        content: @Composable () -> Unit
    ) {
        Column {
            Spacer(Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        }
    }
}
