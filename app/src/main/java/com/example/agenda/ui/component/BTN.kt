package com.example.agenda.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme



enum class BTNType {
    PRIMARY,
    SECONDARY,
}

@Composable
fun BTN(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    type: BTNType = BTNType.PRIMARY,
    containerColor: Color = Theme.Colors.D.color,
    textColor: Color = Theme.Colors.A.color,
    height: Int = 48,
    rounded: Boolean = false
) {
    // altura fixa para os dois
    val btnHeight =  height.dp // ex. 48.dp
    val iconSize = height.dp
    if (type == BTNType.PRIMARY) {
        Button(
            onClick = onClick,
            modifier =if(icon != null || rounded) modifier.size(iconSize) else  modifier
                .height(btnHeight),
            shape = RoundedCornerShape(if(icon != null || rounded) 100.dp else 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = textColor
            ),
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)
        ) {
            if(icon == null) {
                TXT(text, color = textColor)
            }  else {
                Icon(icon, text, tint = textColor, modifier = Modifier.size(iconSize - 6.dp))
            }
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = if(rounded) modifier.size(btnHeight) else modifier
                .height(btnHeight),
            shape = RoundedCornerShape(if(rounded) 100.dp else 4.dp),
            border = BorderStroke(2.dp, containerColor),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = containerColor,
            ),
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)

        ) {
            TXT(text, color = containerColor)
        }
    }
}