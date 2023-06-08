package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.ui.theme.SourceSansProSemiBold
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun CreditCard(
    modifier: Modifier = Modifier,
    cardTypeIcon: @Composable (Modifier) -> Unit,
    number: String,
    name: String,
    date: String,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(modifier = modifier.clip(shape)) {
        val brush = Brush.linearGradient(listOf(Color(0XFFB993D6), Color(0XFF8CA6DB)))
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawRect(brush)
                drawCircle(
                    color = Color(255, 255, 255, 12),
                    radius = this.size.height / 1.5f,
                    center = this.center.plus(Offset(270f, 0f))
                )
            }
        )
        cardTypeIcon.invoke(
            Modifier
                .align(Alignment.TopEnd)
                .padding(top = 31.dp, end = 30.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = number.replace(Regex("\\d{4}"), "$0 "),
            fontSize = 28.sp,
            fontFamily = SourceSansProSemiBold,
            lineHeight = 31.sp,
            color = Color.White,
            style = TextStyle.Default.copy(shadow = Shadow(color = Color.Black, blurRadius = 7f))
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 27.dp, end = 27.dp, bottom = 31.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name.uppercase(Locale.getDefault()),
                fontSize = 20.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 31.sp,
                color = Color.White,
                style = TextStyle.Default.copy(shadow = Shadow(color = Color.Black, blurRadius = 7f))
            )
            Text(
                text = date,
                fontSize = 20.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 31.sp,
                color = Color.White,
                style = TextStyle.Default.copy(shadow = Shadow(color = Color.Black, blurRadius = 7f))
            )
        }
    }
}