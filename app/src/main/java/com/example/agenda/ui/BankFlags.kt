package com.example.agenda.ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.agenda.R


object BankFlags {

    enum class Flags(val title: String, val color: Color,@DrawableRes val painter : Int,val isLight: Boolean) {
        MercadoPago("Mercado Pago", Color(0xFF00B4EA), R.drawable.mercado_pago, true),
        Nubank("Nubank", Color(0xFF810ACF), R.drawable.nubank, false),
        Alelo("Alelo", Color(0xFFCECECE), R.drawable.alelo,true),
        WillBank("Will Bank", Color(0xFFFDD700), R.drawable.will_bank,true),
        Inter("Inter", Color(0xFFF85B2F), R.drawable.inter,true),
        Meliuz("Méliuz", Color(0xFF9C27B0), R.drawable.meliuz,true),
        Wallet("Carteira", Color(0xFF332C2A), R.drawable.carteira,false)
    }
    fun get(title: String): Flags {
        return Flags.entries.find { it.title == title } ?: Flags.Wallet
    }
}
