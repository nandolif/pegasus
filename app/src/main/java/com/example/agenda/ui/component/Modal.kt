package com.example.agenda.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme

object Modal {
    @Composable
    fun Wrapper(
        title: String,
        buttons: @Composable() (() -> Unit)? = null,
        content: @Composable (callback: () -> Unit) -> Unit,
    ): () -> Unit {
        var isVisible by remember { mutableStateOf(false) }
        fun toggle() {
            isVisible = !isVisible
        }
        if (isVisible) {
            AlertDialog(
                containerColor = Theme.Colors.A.color,
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {  }
                            TXT(s = title, fs = 20)
                            IconButton(onClick = {toggle()}) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Theme.Colors.D.color)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth(),
                            color = Theme.Colors.C.color
                        )
                        Spacer(Modifier.height(8.dp))

                    }
                },
                text = {
                    content({ toggle() })
                },
                onDismissRequest = {
                    toggle()
                },
                confirmButton = {
                },
                dismissButton = {
                }
            )
        }
        return { toggle() }
    }

    data class ListItem(
        val text: String,
        val callback: () -> Unit,
    )

    @Composable
    fun <T> List(
        items: List<T> = listOf(),
        extraItem: (@Composable () -> Unit)? = null,
        extraList: (@Composable () -> Unit)? = null,
        content: @Composable (index: Int, item:T) -> Unit,
    ) {
        val scrollState = rememberScrollState()
        if (items.isEmpty()) {

        } else {
            Column(
                Modifier.scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
            ) {
                LazyColumn {
                    itemsIndexed(items) { index, it ->
                        if (extraList != null && index == items.size - 1) {
                            Column {
                                content(index, it)
                                extraList()
                            }
                        } else {
                            content(index, it)
                        }
                    }
                }
                if (extraItem != null) {
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(),
                        color = Theme.Colors.C.color
                    )
                    Spacer(Modifier.height(8.dp))
                    extraItem()
                }
            }

        }

    }

    @Composable
    fun Item(
        isActive: Boolean,
        callback: () -> Unit,
        text: String,
        boxColorEnabled: Color = Theme.Colors.D.color,
        boxColorDisabled: Color = Theme.Colors.C.color,
        icon: ImageVector? = null,
        extraInfo: String? = null,
    ) {
        Column(Modifier.fillMaxWidth()) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        callback()
                    }
                    .height(40.dp)
            ) {
                if (icon == null) {
                    Box(
                        Modifier
                            .background(boxColorEnabled, CircleShape)
                            .size(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .background(
                                    if (isActive) boxColorEnabled else boxColorDisabled,
                                    CircleShape
                                )
                                .size(12.dp)
                        )
                    }
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = boxColorEnabled,
                        modifier = Modifier
                            .width(14.dp)
                            .scale(1.5f)
                    )
                }
                Spacer(Modifier.width(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TXT(text)
                    if (extraInfo != null) {
                        TXT(extraInfo, color = Theme.Colors.C.color)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }


    @Composable
    fun Button(text: String, width: Float, onClick: () -> Unit, type: BTNType = BTNType.PRIMARY) {
        BTN(onClick = onClick, modifier = Modifier.fillMaxWidth(width), type = type, text = text)
    }

    @Composable
    fun Options(
        content: @Composable () -> Unit,
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
