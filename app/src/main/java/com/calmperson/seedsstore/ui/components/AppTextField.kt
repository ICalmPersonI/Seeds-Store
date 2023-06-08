package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.theme.LightViolet
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    password: Boolean = false

) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 14.dp),
            text = label,
            fontSize = 14.sp,
            fontFamily = SourceSansProRegular,
            lineHeight = 22.sp,
            color = Purple
        )
        TextField(
            modifier = modifier
                .border(1.dp, LightViolet, RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            supportingText = if (isError) supportingText else null,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.White,
            ),
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = true,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = placeholder
        )
    }
}