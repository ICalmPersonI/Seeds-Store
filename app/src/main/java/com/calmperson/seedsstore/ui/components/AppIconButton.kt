package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    shape: Shape,
    background: Color,
    tint: Color,
    borderColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .clip(shape)
            .border(1.dp, borderColor, shape)
            .background(if (enabled) background else Color.LightGray)
            .width(78.dp),
        onClick = { if (enabled) onClick.invoke() },
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painter,
            contentDescription = null,
            tint = if (enabled) tint else Color.Gray
        )
    }
}