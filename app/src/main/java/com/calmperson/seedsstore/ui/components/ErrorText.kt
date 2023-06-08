package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier.padding(bottom = 2.dp),
        text = text,
        fontSize = 14.sp,
        fontFamily = SourceSansProRegular,
        lineHeight = 22.sp,
        color = Color.Red
    )
}