package com.example.agenda.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme

@Composable
fun BTN(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonColors(
        containerColor = Theme.Colors.D.color,
        contentColor = Theme.Colors.A.color,
        disabledContainerColor = Theme.Colors.A.color,
        disabledContentColor = Theme.Colors.B.color
    ),
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = buttonColors,
        modifier = modifier
    ) { content() }
}