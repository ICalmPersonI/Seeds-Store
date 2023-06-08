package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun MasterCardIcon(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
        onDraw = {
            drawCircle(
                color = Color(0xFFFFD25F),
                center = this.center.plus(Offset(25f, 0f))
            )
            drawCircle(
                color = Color(0xFFF24E1E).copy(alpha = 0.90f),
                center = this.center.plus(Offset(-25f, 0f))
            )
        }
    )
}