package com.example.agenda.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agenda.ui.Theme
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlin.random.Random

object ColorPicker {

    fun random(): Color {
        val rnd: Random = Random
        return Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    @Composable
    fun Component(
        backgroundColor: MutableState<String>,
        textColor: MutableState<String>,
    ): () -> Unit {
        val controller = rememberColorPickerController()
        val toggle = Modal.Wrapper("Selecionar Cor") { t ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp),
                    controller = controller,
                    initialColor = Color(backgroundColor.value.toULong()),
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        backgroundColor.value = colorEnvelope.color.value.toString()

                    }
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(35.dp),
                        controller = controller,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            BTN(
                                icon = Icons.Default.CompareArrows,
                                text = "Aleatório",
                                height = 35,
                                onClick = {
                                    backgroundColor.value = random().value.toString()
                                    controller.selectByColor(
                                        Color(backgroundColor.value.toULong()),
                                        fromUser = true
                                    )

                                }
                            )

                            BTN(
                                icon = Icons.Default.TextFields,
                                text = "Light Text",
                                containerColor = Theme.Colors.B.color,
                                textColor = Theme.Colors.D.color,
                                height = 35,
                                onClick = {
                                    textColor.value = Theme.Colors.D.color.value.toString()

                                }
                            )

                            BTN(
                                icon = Icons.Default.TextFields,
                                text = "Dark Text",
                                containerColor = Theme.Colors.D.color,
                                textColor = Theme.Colors.A.color,
                                height = 35,
                                onClick = {
                                    textColor.value = Theme.Colors.A.color.value.toString()
                                }
                            )
                        }
                    }

                }
                Spacer(Modifier.height(20.dp))
                BTN(
                    onClick = {
                    },
                    containerColor = Color(backgroundColor.value.toULong()),
                    textColor = Color(textColor.value.toULong()),
                    text = "Exemplo"
                )
                Modal.Options {
                    Modal.Button(
                        text = "Fechar",
                        width = .5f,
                        onClick = t,
                        type = BTNType.SECONDARY
                    )
                    Modal.Button(
                        text = "Confirmar",
                        width = 1f,
                        onClick = t,
                        type = BTNType.PRIMARY
                    )
                }
            }
        }
        return toggle
    }

}