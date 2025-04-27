package com.example.agenda.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.agenda.ui.Theme

@Composable
fun OTF(
    modifier: Modifier = Modifier,
    ph: String? = null,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    ti: @Composable (() -> Unit)? = null,
    ro: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        readOnly = ro,
        placeholder = {
            if (ph != null) {
                TXT(ph)
            }
        },
        label = {
            TXT(label)
        },
        value = value,
        onValueChange = onValueChange,
        trailingIcon =ti ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Theme.Colors.D.color,
            unfocusedBorderColor = Theme.Colors.C.color,
            cursorColor = Theme.Colors.C.color,
            focusedLabelColor = Theme.Colors.D.color,
            unfocusedLabelColor = Theme.Colors.C.color,
            unfocusedTextColor = Theme.Colors.D.color,
            focusedTextColor = Theme.Colors.D.color,
        ),
        modifier = modifier,
        keyboardOptions = keyboardOptions,
    )
}