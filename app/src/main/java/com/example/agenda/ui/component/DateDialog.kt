package com.example.agenda.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.app.helps.Date
import com.example.agenda.app.objects.DayMonthYearObject
import com.example.agenda.ui.Theme


object DateDialog {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Component(date: MutableState<DayMonthYearObject?>): () -> Unit {
        var isDialogVisible by remember { mutableStateOf(false) }

        fun toggle() {
            isDialogVisible = !isDialogVisible
        }


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
                    primaryContainer = Theme.Colors.D.color,
                    onPrimaryContainer = Theme.Colors.A.color,
                    inversePrimary = Theme.Colors.D.color,

                    // Secondary accents
                    secondary = Theme.Colors.D.color,
                    onSecondary = Theme.Colors.A.color,
                    secondaryContainer = Theme.Colors.A.color,
                    onSecondaryContainer = Theme.Colors.D.color,

                    // Tertiary accents
                    tertiary = Theme.Colors.D.color,
                    onTertiary = Theme.Colors.A.color,
                    tertiaryContainer = Theme.Colors.A.color,
                    onTertiaryContainer = Theme.Colors.D.color,

                    // Core backgrounds & surfaces
                    background = Theme.Colors.A.color,
                    onBackground = Theme.Colors.D.color,
                    surface = Theme.Colors.A.color,
                    onSurface = Theme.Colors.D.color,
                    surfaceVariant = Theme.Colors.A.color,
                    onSurfaceVariant = Theme.Colors.D.color,

                    // Surface tint & inverses
                    surfaceTint = Theme.Colors.D.color,
                    inverseSurface = Theme.Colors.D.color,
                    inverseOnSurface = Theme.Colors.A.color,

                    // Error slots
                    error = Theme.Colors.D.color,
                    onError = Theme.Colors.A.color,
                    errorContainer = Theme.Colors.A.color,
                    onErrorContainer = Theme.Colors.D.color,

                    // Miscellaneous
                    outline = Theme.Colors.D.color,
                    outlineVariant = Theme.Colors.A.color,
                    scrim = Theme.Colors.A.color,
                    surfaceBright = Theme.Colors.D.color,
                    surfaceContainer = Theme.Colors.A.color,
                    surfaceContainerHigh = Theme.Colors.A.color,
                    surfaceContainerHighest = Theme.Colors.D.color,
                    surfaceContainerLow = Theme.Colors.A.color,
                    surfaceContainerLowest = Theme.Colors.A.color,
                    surfaceDim = Theme.Colors.A.color
                ),
                content = {
                    DatePickerDialog(
                        modifier = Modifier.padding(8.dp),
                        onDismissRequest = {},
                        confirmButton = {
                            BTN(onClick = {
                                date.value = Date.longToDayMonthYear(
                                    dateState.selectedDateMillis ?: System.currentTimeMillis()
                                )
                                toggle()

                            }, text = "Confirmar")
                        },
                        dismissButton = {
                            BTN(onClick = {
                                toggle()
                            }, text = "Cancelar", type = BTNType.SECONDARY)
                        },
                        colors = DatePickerDefaults.colors(
                            containerColor = Theme.Colors.A.color,
                            titleContentColor = Theme.Colors.D.color,
                            headlineContentColor = Theme.Colors.D.color,
                            weekdayContentColor = Theme.Colors.D.color,
                            subheadContentColor = Theme.Colors.D.color,
                            navigationContentColor = Theme.Colors.D.color,
                            yearContentColor = Theme.Colors.D.color,
                            disabledYearContentColor = Theme.Colors.A.color,
                            currentYearContentColor = Theme.Colors.D.color,
                            selectedYearContentColor = Theme.Colors.A.color,
                            disabledSelectedYearContentColor = Theme.Colors.A.color,
                            selectedYearContainerColor = Theme.Colors.D.color,
                            disabledSelectedYearContainerColor = Theme.Colors.A.color,
                            dayContentColor = Theme.Colors.D.color,
                            disabledDayContentColor = Theme.Colors.A.color,
                            selectedDayContentColor = Theme.Colors.A.color,
                            disabledSelectedDayContentColor = Theme.Colors.A.color,
                            selectedDayContainerColor = Theme.Colors.D.color,
                            disabledSelectedDayContainerColor = Theme.Colors.A.color,
                            todayContentColor = Theme.Colors.D.color,
                            todayDateBorderColor = Theme.Colors.D.color,
                            dayInSelectionRangeContainerColor = Theme.Colors.A.color,
                            dayInSelectionRangeContentColor = Theme.Colors.D.color,
                            dividerColor = Theme.Colors.D.color,
                            dateTextFieldColors = TextFieldColors(
                                // Text colors
                                focusedTextColor = Theme.Colors.D.color,
                                unfocusedTextColor = Theme.Colors.D.color,
                                disabledTextColor = Theme.Colors.D.color,
                                errorTextColor = Theme.Colors.D.color,
                                // Container/background
                                focusedContainerColor = Theme.Colors.A.color,
                                unfocusedContainerColor = Theme.Colors.A.color,
                                disabledContainerColor = Theme.Colors.A.color,
                                errorContainerColor = Theme.Colors.A.color,
                                // Cursor
                                cursorColor = Theme.Colors.D.color,
                                errorCursorColor = Theme.Colors.D.color,
                                // Selection
                                textSelectionColors = TextSelectionColors(
                                    handleColor = Theme.Colors.D.color,
                                    backgroundColor = Theme.Colors.D.color
                                ),
                                // Indicators (underline)
                                focusedIndicatorColor = Theme.Colors.D.color,
                                unfocusedIndicatorColor = Theme.Colors.D.color,
                                disabledIndicatorColor = Theme.Colors.A.color,
                                errorIndicatorColor = Theme.Colors.D.color,
                                // Leading icon
                                focusedLeadingIconColor = Theme.Colors.D.color,
                                unfocusedLeadingIconColor = Theme.Colors.D.color,
                                disabledLeadingIconColor = Theme.Colors.A.color,
                                errorLeadingIconColor = Theme.Colors.D.color,
                                // Trailing icon
                                focusedTrailingIconColor = Theme.Colors.D.color,
                                unfocusedTrailingIconColor = Theme.Colors.D.color,
                                disabledTrailingIconColor = Theme.Colors.A.color,
                                errorTrailingIconColor = Theme.Colors.D.color,
                                // Label
                                focusedLabelColor = Theme.Colors.D.color,
                                unfocusedLabelColor = Theme.Colors.D.color,
                                disabledLabelColor = Theme.Colors.A.color,
                                errorLabelColor = Theme.Colors.D.color,
                                // Placeholder
                                focusedPlaceholderColor = Theme.Colors.D.color,
                                unfocusedPlaceholderColor = Theme.Colors.D.color,
                                disabledPlaceholderColor = Theme.Colors.A.color,
                                errorPlaceholderColor = Theme.Colors.D.color,
                                // Supporting text
                                focusedSupportingTextColor = Theme.Colors.D.color,
                                unfocusedSupportingTextColor = Theme.Colors.D.color,
                                disabledSupportingTextColor = Theme.Colors.A.color,
                                errorSupportingTextColor = Theme.Colors.D.color,
                                // Prefix
                                focusedPrefixColor = Theme.Colors.D.color,
                                unfocusedPrefixColor = Theme.Colors.D.color,
                                disabledPrefixColor = Theme.Colors.A.color,
                                errorPrefixColor = Theme.Colors.D.color,
                                // Suffix
                                focusedSuffixColor = Theme.Colors.D.color,
                                unfocusedSuffixColor = Theme.Colors.D.color,
                                disabledSuffixColor = Theme.Colors.A.color,
                                errorSuffixColor = Theme.Colors.D.color
                            )
                        )
                    ) {
                        DatePicker(
                            state = dateState,
                            showModeToggle = false
                        )
                    }
                }
            )

        }

        return { toggle() }
    }
}