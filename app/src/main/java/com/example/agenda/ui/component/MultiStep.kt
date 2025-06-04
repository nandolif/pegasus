package com.example.agenda.ui.component

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme


object MultiStep {


    @Composable
    fun <T> List(
        multiStepItems: List<T>,
        fallback: (@Composable () -> Unit)? = null,
        content: @Composable (item: T) -> Unit,
    ) {
        Column {
            if (multiStepItems.isEmpty() && fallback != null) {
                fallback()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize(.95f),
                    userScrollEnabled = true,
                ) {
                    items(multiStepItems) {
                        content(it)
                    }

                }
            }
        }
    }

    data class ItemData(val name: String, val callback: () -> Unit)

    @Composable
    fun Item(item: ItemData, next: () -> Unit) {
        Column(modifier = Modifier.clickable { item.callback();next() }) {
            TXT(item.name, fs = 20)
            HorizontalDivider(thickness = 2.dp, color = Theme.Colors.B.color)
        }
    }
    data class StepData(val isAvailable: Boolean = true, val content: @Composable () -> Unit)
    @Composable
    fun Wrapper(
        finish: () -> Unit,
        steps: (next: () -> Unit, previous: () -> Unit, index: Int) -> List<StepData>,
    ) {
        var currentStep by remember { mutableStateOf(0) }
        val stepsItems = steps(
            {
                currentStep++
            },
            {
                if (currentStep == 0) return@steps
                currentStep--
            }, currentStep
        )

        val items = stepsItems.filter { it.isAvailable }
        val currentStepContent = items.getOrNull(currentStep) ?: return finish()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight(.9f)
        ) {
            currentStepContent.content()
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (i in items.indices) {
                    Box(
                        Modifier
                            .background(
                                if (i == currentStep) Theme.Colors.D.color else Theme.Colors.B.color,
                                CircleShape
                            )
                            .size(12.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Step(title: String, previous: () -> Unit, index: Int, content: @Composable () -> Unit) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    if (index != 0) {
                        BTN(
                            text = "Voltar",
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            onClick = previous,
                        )
                    }
                    TXT(title, fs = 20)
                }
                HorizontalDivider(thickness = 2.dp, color = Theme.Colors.B.color)
            }
            Spacer(Modifier.height(30.dp))
            content()
        }
    }
}
