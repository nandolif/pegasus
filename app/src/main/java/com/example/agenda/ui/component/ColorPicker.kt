package com.example.agenda.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

object ColorPicker {
    @Composable
    fun Component(decline:() -> Unit, backgroundColor: MutableState<String>, textColor: MutableState<String>) {
        val controller = rememberColorPickerController()
        var isChangingBackground by remember { mutableStateOf(true) }

        Modal.Component("Selecionar Cor") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(10.dp),
                    controller = controller,
                    initialColor = Color(if (isChangingBackground) backgroundColor.value.toULong() else textColor.value.toULong()),
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        if (isChangingBackground) {
                            backgroundColor.value = colorEnvelope.color.value.toString()
                        } else {
                            textColor.value = colorEnvelope.color.value.toString()
                        }
                    }
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(35.dp),
                    controller = controller,
                )
                Spacer(Modifier.height(20.dp))
                BTN(
                    onClick = {
                        isChangingBackground = !isChangingBackground
                    }, containerColor = Color(backgroundColor.value.toULong()),textColor = Color(textColor.value.toULong()), text =
                        if (!isChangingBackground) "Mudando texto" else "Mudando fundo"
                )
                Modal.Options {
                    Modal.Button(
                        text = "Cancelar",
                        width = .5f,
                        onClick = decline,
                        type = BTNType.SECONDARY
                    )
                    Modal.Button(
                        text = "Confirmar",
                        width = 1f,
                        onClick = decline
                    )
                }
            }
        }
    }

}