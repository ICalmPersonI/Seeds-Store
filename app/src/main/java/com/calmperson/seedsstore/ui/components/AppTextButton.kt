package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.theme.Green
import com.calmperson.seedsstore.ui.theme.LightViolet
import com.calmperson.seedsstore.ui.theme.SourceSansProSemiBold

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = RoundedCornerShape(8.dp),
    border: BorderStroke = BorderStroke(1.dp, LightViolet),
    icon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = Green,
        contentColor = Color.White
    ),
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier.height(56.dp),
        shape = shape,
        border = if (enabled) border else null,
        onClick = { if (enabled) onClick.invoke() },
        colors = if (enabled) colors else ButtonDefaults.textButtonColors(
            containerColor = Color.LightGray,
            contentColor = Color.DarkGray
        )
    ) {
        Row() {
            icon?.let {
                it.invoke()
                Spacer(modifier = Modifier.width(17.dp))
            }
            Text(
                text = text.uppercase(),
                fontSize = 15.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 18.sp
            )
        }
    }
}