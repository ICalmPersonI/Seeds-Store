package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(27.dp))
            .height(54.dp),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.Search, contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                fontSize = 17.sp,
                fontFamily = SourceSansProRegular,
                lineHeight = 22.sp
            )
        }
    )
}