package com.example.agenda.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agenda.ui.Theme

@Composable
fun TXT(s: String,color: Color = Theme.Colors.D.color, fs: Int = 16,  maxLines: Int = Int.MAX_VALUE){
    Text(text=s, color = color, fontSize = fs.sp, maxLines = maxLines)
}