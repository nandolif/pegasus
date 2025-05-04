package com.example.agenda.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme

object Form {
    @Composable
    fun Wrapper(content: @Composable () -> Unit) {
        Column {
            content()
        }
    }

    @Composable
    fun Input(
        icon: ImageVector,
        placeholder: String,
        value: String,
        onValueChange: (String) -> Unit,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    ) {
        _Row(icon, {}) {
            TextField(
                singleLine = true,
                modifier = Modifier
                    .height(60.dp)
                    .offset(x = (-16).dp),
                keyboardOptions = keyboardOptions,
                value = value,
                onValueChange = onValueChange,
                placeholder = { TXT(placeholder) },
                colors = TextFieldColors(
                    focusedTextColor = Theme.Colors.D.color,
                    unfocusedTextColor = Theme.Colors.D.color,
                    disabledTextColor = Theme.Colors.D.color,
                    errorTextColor = Theme.Colors.D.color,
                    focusedContainerColor = Theme.Colors.A.color,
                    unfocusedContainerColor = Theme.Colors.A.color,
                    disabledContainerColor = Theme.Colors.A.color,
                    errorContainerColor = Theme.Colors.A.color,
                    cursorColor = Theme.Colors.D.color,
                    errorCursorColor = Theme.Colors.D.color,
                    textSelectionColors = TextSelectionColors(
                        handleColor = Theme.Colors.A.color,
                        backgroundColor = Theme.Colors.A.color
                    ),
                    focusedIndicatorColor = Theme.Colors.A.color,
                    unfocusedIndicatorColor = Theme.Colors.A.color,
                    disabledIndicatorColor = Theme.Colors.A.color,
                    errorIndicatorColor = Theme.Colors.A.color,
                    focusedLeadingIconColor = Theme.Colors.A.color,
                    unfocusedLeadingIconColor = Theme.Colors.A.color,
                    disabledLeadingIconColor = Theme.Colors.A.color,
                    errorLeadingIconColor = Theme.Colors.A.color,
                    focusedTrailingIconColor = Theme.Colors.A.color,
                    unfocusedTrailingIconColor = Theme.Colors.A.color,
                    disabledTrailingIconColor = Theme.Colors.A.color,
                    errorTrailingIconColor = Theme.Colors.A.color,
                    focusedLabelColor = Theme.Colors.A.color,
                    unfocusedLabelColor = Theme.Colors.A.color,
                    disabledLabelColor = Theme.Colors.A.color,
                    errorLabelColor = Theme.Colors.A.color,
                    focusedPlaceholderColor = Theme.Colors.D.color,
                    unfocusedPlaceholderColor = Theme.Colors.D.color,
                    disabledPlaceholderColor = Theme.Colors.D.color,
                    errorPlaceholderColor = Theme.Colors.D.color,
                    focusedSupportingTextColor = Theme.Colors.A.color,
                    unfocusedSupportingTextColor = Theme.Colors.A.color,
                    disabledSupportingTextColor = Theme.Colors.A.color,
                    errorSupportingTextColor = Theme.Colors.A.color,
                    focusedPrefixColor = Theme.Colors.A.color,
                    unfocusedPrefixColor = Theme.Colors.A.color,
                    disabledPrefixColor = Theme.Colors.A.color,
                    errorPrefixColor = Theme.Colors.A.color,
                    focusedSuffixColor = Theme.Colors.A.color,
                    unfocusedSuffixColor = Theme.Colors.A.color,
                    disabledSuffixColor = Theme.Colors.A.color,
                    errorSuffixColor = Theme.Colors.A.color
                )
            )
        }
    }

    @Composable
    fun Row(
        icon: ImageVector,
        callback: () -> Unit,
        placeholder: String,
        text: String?,
        extraInfo: String? = null,
    ) {
        _Row(icon, callback) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TXT(text ?: placeholder)
                if (extraInfo != null) {
                    Row {
                        TXT(extraInfo, color = Theme.Colors.C.color)
                        Spacer(Modifier.width(30.dp))

                    }
                }
            }

        }
    }

    @Composable
    private fun _Row(
        icon: ImageVector,
        callback: () -> Unit,
        content: @Composable () -> Unit,
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { callback() }
                    .height(60.dp)
            ) {
                Spacer(Modifier.width(30.dp))
                Icon(icon, contentDescription = "Icon", tint = Theme.Colors.D.color)
                Spacer(Modifier.width(30.dp))
                content()
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = Theme.Colors.C.color
            )
        }
    }


}