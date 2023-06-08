package com.calmperson.seedsstore.ui.screen.account

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.AppTextField
import com.calmperson.seedsstore.ui.components.ErrorText
import com.calmperson.seedsstore.ui.state.SignInCallback
import com.calmperson.seedsstore.ui.state.SignUpCallback
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProBold
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    signIn: (String, String, Boolean) -> SharedFlow<SignInCallback?>,
    navigateToAccountScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("android@gmail.com") }
    var password by remember { mutableStateOf("1234") }
    var rememberMe by remember { mutableStateOf(false) }

    var invalidEmail by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(31.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sing_in),
                fontSize = 30.sp,
                fontFamily = SourceSansProBold,
                lineHeight = 41.sp,
                textAlign = TextAlign.Center
            )
            AppTextField(
                label = stringResource(R.string.email),
                value = email,
                onValueChange = {
                    email = it
                    invalidEmail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                isError = email.isBlank() || invalidEmail,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(
                            if (invalidEmail) R.string.invalid_email_address
                            else R.string.field_is_empty
                        ),
                    )
                }
            )
            AppTextField(
                label = stringResource(R.string.password),
                value = password,
                onValueChange = { password = it },
                isError = password.isBlank() || password.length < 3,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(R.string.field_is_empty),
                    )
                },
                password = true
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.align(Alignment.CenterStart)) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = !rememberMe }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(R.string.remember_me),
                        fontSize = 16.sp,
                        fontFamily = SourceSansProRegular,
                        lineHeight = 20.sp
                    )
                }
            }
            AppTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sing_in),
                onClick = {
                    if ((email.isNotBlank() && !invalidEmail)
                        && (password.isNotBlank() && password.length > 3)) {
                        CoroutineScope(Dispatchers.Main).launch {
                            signIn(email, password, rememberMe).collect { callback ->
                                callback?.let {
                                    if (callback.success) {
                                        navigateToAccountScreen.invoke()
                                    } else {
                                        if (callback.message != -1) {
                                            Toast.makeText(
                                                context,
                                                context.getText(callback.message),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            )
            AppTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration),
                onClick = navigateToSignUpScreen
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun SingInScreenPreview() {
    SeedsStoreTheme {
        SignInScreen(
            signIn = { _, _, _ -> MutableStateFlow(null) },
            navigateToAccountScreen = {},
            navigateToSignUpScreen = {}
        )
    }
}