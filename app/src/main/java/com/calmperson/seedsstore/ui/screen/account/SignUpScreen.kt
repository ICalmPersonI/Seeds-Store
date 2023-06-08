package com.calmperson.seedsstore.ui.screen.account

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.AppTextField
import com.calmperson.seedsstore.ui.components.ErrorText
import com.calmperson.seedsstore.ui.screen.Screen
import com.calmperson.seedsstore.ui.state.SignInCallback
import com.calmperson.seedsstore.ui.state.SignUpCallback
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProBold
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUp: (String, String, String, String) -> SharedFlow<SignUpCallback?>,
    navigateToAccountScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var differentPasswords by remember { mutableStateOf(false) }
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
                text = stringResource(R.string.sing_up),
                fontSize = 30.sp,
                fontFamily = SourceSansProBold,
                lineHeight = 41.sp,
                textAlign = TextAlign.Center
            )
            AppTextField(
                label = stringResource(R.string.first_name),
                value = firstName,
                onValueChange = { firstName = it },
                isError = firstName.isBlank() || firstName.length < 2,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(
                            if (firstName.length < 2) R.string.first_name_is_too_short
                            else R.string.field_is_empty
                        )
                    )
                }
            )
            AppTextField(
                label = stringResource(R.string.last_name),
                value = lastName,
                onValueChange = { lastName = it },
                isError = lastName.isBlank() || lastName.length < 2,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(
                            if (lastName.length < 2) R.string.last_name_is_too_short
                            else R.string.field_is_empty
                        ),
                    )
                }
            )
            AppTextField(
                label = stringResource(R.string.email),
                value = email,
                onValueChange = {
                    email = it
                    invalidEmail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                isError = invalidEmail || email.isBlank(),
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(if (invalidEmail) R.string.invalid_email_address else R.string.field_is_empty),
                    )
                }
            )
            AppTextField(
                label = stringResource(R.string.password),
                value = password,
                onValueChange = { password = it },
                isError = differentPasswords || password.length < 3,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(
                            if (differentPasswords) R.string.passwords_are_different
                            else R.string.password_is_too_short
                        ),
                    )
                },
                password = true

            )
            AppTextField(
                label = stringResource(R.string.confirm_password),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    differentPasswords = password != confirmPassword
                },
                isError = differentPasswords || confirmPassword.length < 3,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(
                            if (differentPasswords) R.string.passwords_are_different
                            else R.string.password_is_too_short
                        ),
                    )
                },
                password = true
            )
            Spacer(modifier = Modifier.height(1.dp))
            AppTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sing_up),
                onClick = {
                    if (!differentPasswords
                        && (!invalidEmail && email.isNotBlank())
                        && (firstName.isNotBlank() && firstName.length > 1)
                        && (lastName.isNotBlank() && lastName.length > 1)
                        && (password.length > 3 && confirmPassword.length > 3)) {
                        CoroutineScope(Dispatchers.IO).launch {
                            signUp(firstName, lastName, email, password).collect { callback ->
                                callback?.let {
                                    if (callback.success) {
                                        navigateToAccountScreen.invoke()
                                    } else {
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
            )
            AppTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.i_have_an_account),
                onClick = navigateToSignInScreen
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
private fun SingUpScreenPreview() {
    SeedsStoreTheme {
        SignUpScreen(
            signUp = { _, _, _, _ -> MutableStateFlow(null) },
            navigateToAccountScreen = {},
            navigateToSignInScreen = {}
        )
    }
}