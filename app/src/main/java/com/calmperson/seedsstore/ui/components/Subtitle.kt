package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.ui.theme.SourceSansProBold

@Composable
fun Subtitle(
    modifier: Modifier = Modifier,
    text: String,
    actions: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontSize = 22.sp,
            fontFamily = SourceSansProBold,
            lineHeight = 22.sp
        )
        actions?.let {
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                it.invoke()
            }
        }
    }
}