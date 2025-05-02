package com.example.agenda.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    isDialogVisible: Boolean,
    onAccept: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return true
        }

        override fun isSelectableYear(year: Int): Boolean {
            return true
        }
    })
    if (isDialogVisible) {
        MaterialTheme(
            colorScheme = darkColorScheme(
                // Accent & primary surfaces
                primary = Theme.Colors.D.color,
                onPrimary = Theme.Colors.A.color,
                primaryContainer = Theme.Colors.C.color,
                onPrimaryContainer = Theme.Colors.A.color,
                inversePrimary = Theme.Colors.C.color,

                // Secondary accents
                secondary = Theme.Colors.C.color,
                onSecondary = Theme.Colors.A.color,
                secondaryContainer = Theme.Colors.B.color,
                onSecondaryContainer = Theme.Colors.D.color,

                // Tertiary accents
                tertiary = Theme.Colors.C.color,
                onTertiary = Theme.Colors.A.color,
                tertiaryContainer = Theme.Colors.B.color,
                onTertiaryContainer = Theme.Colors.D.color,

                // Core backgrounds & surfaces
                background = Theme.Colors.A.color,
                onBackground = Theme.Colors.D.color,
                surface = Theme.Colors.A.color,
                onSurface = Theme.Colors.D.color,
                surfaceVariant = Theme.Colors.B.color,
                onSurfaceVariant = Theme.Colors.C.color,

                // Surface tint & inverses
                surfaceTint = Theme.Colors.C.color,
                inverseSurface = Theme.Colors.D.color,
                inverseOnSurface = Theme.Colors.A.color,

                // Error slots
                error = Theme.Colors.C.color,
                onError = Theme.Colors.A.color,
                errorContainer = Theme.Colors.B.color,
                onErrorContainer = Theme.Colors.D.color,

                // Miscellaneous
                outline = Theme.Colors.C.color,
                outlineVariant = Theme.Colors.B.color,
                scrim = Theme.Colors.A.color,
                surfaceBright = Theme.Colors.C.color,
                surfaceContainer = Theme.Colors.B.color,
                surfaceContainerHigh = Theme.Colors.B.color,
                surfaceContainerHighest = Theme.Colors.C.color,
                surfaceContainerLow = Theme.Colors.A.color,
                surfaceContainerLowest = Theme.Colors.B.color,
                surfaceDim = Theme.Colors.B.color
            ),
            content = {
                DatePickerDialog(
                    modifier = Modifier.padding(8.dp),
                    onDismissRequest = {},
                    confirmButton = {
                        BTN(onClick = {
                            onAccept(dateState.selectedDateMillis ?: System.currentTimeMillis())
                        }, text = "Confirmar")
                    },
                    dismissButton = {
                        BTN(onClick = {
                            onDismiss()
                        }, text = "Cancelar")
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = Theme.Colors.A.color,
                        titleContentColor = Theme.Colors.D.color,
                        headlineContentColor = Theme.Colors.D.color,
                        weekdayContentColor = Theme.Colors.D.color,
                        subheadContentColor = Theme.Colors.D.color,
                        navigationContentColor = Theme.Colors.D.color,
                        yearContentColor = Theme.Colors.D.color,
                        disabledYearContentColor = Theme.Colors.B.color,
                        currentYearContentColor = Theme.Colors.D.color,
                        selectedYearContentColor = Theme.Colors.A.color,
                        disabledSelectedYearContentColor = Theme.Colors.B.color,
                        selectedYearContainerColor = Theme.Colors.C.color,
                        disabledSelectedYearContainerColor = Theme.Colors.B.color,
                        dayContentColor = Theme.Colors.D.color,
                        disabledDayContentColor = Theme.Colors.B.color,
                        selectedDayContentColor = Theme.Colors.A.color,
                        disabledSelectedDayContentColor = Theme.Colors.B.color,
                        selectedDayContainerColor = Theme.Colors.C.color,
                        disabledSelectedDayContainerColor = Theme.Colors.B.color,
                        todayContentColor = Theme.Colors.D.color,
                        todayDateBorderColor = Theme.Colors.D.color,
                        dayInSelectionRangeContainerColor = Theme.Colors.B.color,
                        dayInSelectionRangeContentColor = Theme.Colors.D.color,
                        dividerColor = Theme.Colors.C.color,
                        dateTextFieldColors = TextFieldColors(
                            // Text colors
                            focusedTextColor = Theme.Colors.D.color,
                            unfocusedTextColor = Theme.Colors.D.color,
                            disabledTextColor = Theme.Colors.D.color,
                            errorTextColor = Theme.Colors.D.color,
                            // Container/background
                            focusedContainerColor = Theme.Colors.B.color,
                            unfocusedContainerColor = Theme.Colors.A.color,
                            disabledContainerColor = Theme.Colors.A.color,
                            errorContainerColor = Theme.Colors.A.color,
                            // Cursor
                            cursorColor = Theme.Colors.D.color,
                            errorCursorColor = Theme.Colors.D.color,
                            // Selection
                            textSelectionColors = TextSelectionColors(
                                handleColor = Theme.Colors.D.color,
                                backgroundColor = Theme.Colors.C.color
                            ),
                            // Indicators (underline)
                            focusedIndicatorColor = Theme.Colors.D.color,
                            unfocusedIndicatorColor = Theme.Colors.C.color,
                            disabledIndicatorColor = Theme.Colors.B.color,
                            errorIndicatorColor = Theme.Colors.D.color,
                            // Leading icon
                            focusedLeadingIconColor = Theme.Colors.D.color,
                            unfocusedLeadingIconColor = Theme.Colors.C.color,
                            disabledLeadingIconColor = Theme.Colors.B.color,
                            errorLeadingIconColor = Theme.Colors.D.color,
                            // Trailing icon
                            focusedTrailingIconColor = Theme.Colors.D.color,
                            unfocusedTrailingIconColor = Theme.Colors.C.color,
                            disabledTrailingIconColor = Theme.Colors.B.color,
                            errorTrailingIconColor = Theme.Colors.D.color,
                            // Label
                            focusedLabelColor = Theme.Colors.D.color,
                            unfocusedLabelColor = Theme.Colors.C.color,
                            disabledLabelColor = Theme.Colors.B.color,
                            errorLabelColor = Theme.Colors.D.color,
                            // Placeholder
                            focusedPlaceholderColor = Theme.Colors.C.color,
                            unfocusedPlaceholderColor = Theme.Colors.C.color,
                            disabledPlaceholderColor = Theme.Colors.B.color,
                            errorPlaceholderColor = Theme.Colors.D.color,
                            // Supporting text
                            focusedSupportingTextColor = Theme.Colors.D.color,
                            unfocusedSupportingTextColor = Theme.Colors.C.color,
                            disabledSupportingTextColor = Theme.Colors.B.color,
                            errorSupportingTextColor = Theme.Colors.D.color,
                            // Prefix
                            focusedPrefixColor = Theme.Colors.D.color,
                            unfocusedPrefixColor = Theme.Colors.C.color,
                            disabledPrefixColor = Theme.Colors.B.color,
                            errorPrefixColor = Theme.Colors.D.color,
                            // Suffix
                            focusedSuffixColor = Theme.Colors.D.color,
                            unfocusedSuffixColor = Theme.Colors.C.color,
                            disabledSuffixColor = Theme.Colors.B.color,
                            errorSuffixColor = Theme.Colors.D.color
                        )
                    )
                ) {
                    DatePicker(
                        state = dateState,
                        showModeToggle = true
                    )
                }
            }
        )

    }
}