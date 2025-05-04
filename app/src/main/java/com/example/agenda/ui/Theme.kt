package com.example.agenda.ui

import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AssignmentLate
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons as I

object Theme {
    enum class Colors(val color: Color) {
        A(Color(0xFF1F2025)),
        B(Color(0xFF2F3038)),
        C(Color(0xFF3B3D46)),
        D(Color(0xFFC4CCE5)),
    }

    enum class Icons(val icon: ImageVector) {
        Goal(I.Default.Star),
        Transaction(I.Default.AttachMoney),
        Event(I.Default.DateRange),
        Bank(I.Default.AccountBalanceWallet),
        Backup(I.Default.Backup),
        Restore(I.Default.Cloud),
        TransactionCategory(I.Default.Category),
        EventCategory(I.Default.AssignmentLate),
        Text(I.Default.Sort),
        Money(I.Default.Money),
        Transfer(I.Default.KeyboardDoubleArrowRight)
    }
}