package com.example.agenda.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.agenda.ui.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Enum<T>> EDM(
    selected: T?,
    crossinline onSelectedChange: (T) -> Unit,
    label: String = "",
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val items = enumValues<T>()



    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OTF(
            value = if(selected==null)"" else selected.name,
            onValueChange = {},
            ph = "",
            label = label,
            ti = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            ro = true,
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Theme.Colors.B.color,
        ){
            items.forEach { item ->
                DropdownMenuItem(
                    text = { TXT(s = item.name) },
                    onClick = {
                        onSelectedChange(item)
                        expanded = false
                    },
                    colors = MenuItemColors(
                        textColor = Theme.Colors.D.color,
                        leadingIconColor = Theme.Colors.B.color,
                        trailingIconColor = Theme.Colors.B.color,
                        disabledTextColor = Theme.Colors.A.color,
                        disabledLeadingIconColor = Theme.Colors.A.color,
                        disabledTrailingIconColor = Theme.Colors.A.color
                    )
                )
            }
        }
    }
}