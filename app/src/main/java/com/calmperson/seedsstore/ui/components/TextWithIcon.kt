package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.height(24.dp),
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(26.dp))
        Text(
            text = text,
            fontSize = 17.sp,
            fontFamily = SourceSansProRegular,
            lineHeight = 25.sp,
            color = Purple
        )
    }

}