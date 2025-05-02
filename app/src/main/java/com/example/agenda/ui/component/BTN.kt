package com.example.agenda.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme


data class BTNCollors(
    val containerColor: Color,
    val contentColor: Color = Theme.Colors.A.color,
    val disabledContainerColor: Color = Theme.Colors.A.color,
    val disabledContentColor: Color = Theme.Colors.B.color,
)

enum class BTNType {
    PRIMARY,
    SECONDARY,
}

@Composable
fun BTN(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: BTNType = BTNType.PRIMARY,
    containerColor: Color = Theme.Colors.D.color,
    textColor: Color = Theme.Colors.A.color,
    text: String,
) {
    // altura fixa para os dois
    val btnHeight =  48.dp // ex. 48.dp

    if (type == BTNType.PRIMARY) {
        Button(
            onClick = onClick,
            modifier = modifier
                .height(btnHeight),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = textColor
            ),
        ) {
            TXT(text, color = textColor)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .height(btnHeight),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(2.dp, containerColor),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = containerColor
            )
        ) {
            TXT(text, color = containerColor)
        }
    }
}