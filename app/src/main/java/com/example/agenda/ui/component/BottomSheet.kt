package com.example.agenda.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme
import kotlinx.coroutines.launch

object BottomSheet {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Wrapper(content: @Composable (toggle: () -> Unit) -> Unit): () -> Unit {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        fun toggle() {
            scope.launch { if (sheetState.isVisible) sheetState.hide() else sheetState.show() }
        }

        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = { scope.launch { sheetState.hide() } },
                sheetState = sheetState,
                containerColor = Theme.Colors.A.color
            ) {
                Column(modifier = Modifier.fillMaxHeight(.7f)) {

                content({ toggle() })
                }
            }
        }
        return { toggle() }
    }
}