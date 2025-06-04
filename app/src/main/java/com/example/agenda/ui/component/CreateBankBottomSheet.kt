package com.example.agenda.ui.component

import androidx.compose.runtime.Composable

@Composable
fun CreateBankBottomSheet(): () -> Unit {
   val toggle =  BottomSheet.Wrapper {toggle ->
        TXT("Criar Banco")
    }

    return toggle
}