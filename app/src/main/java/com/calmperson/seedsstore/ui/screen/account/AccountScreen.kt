package com.calmperson.seedsstore.ui.screen.account

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.AppTextField
import com.calmperson.seedsstore.ui.components.CreditCard
import com.calmperson.seedsstore.ui.components.ErrorText
import com.calmperson.seedsstore.ui.components.MasterCardIcon
import com.calmperson.seedsstore.ui.components.Subtitle
import com.calmperson.seedsstore.ui.components.TextWithIcon
import com.calmperson.seedsstore.ui.state.AccountScreenState
import com.calmperson.seedsstore.ui.theme.DarkViolet
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular
import com.calmperson.seedsstore.ui.theme.SourceSansProSemiBold

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    state: AccountScreenState,
    changeName: (String, String) -> Unit,
    changeEmail: (String) -> Unit,
    changePassword: (String) -> Unit,
    logOut: () -> Unit,
    navigateToCategories: () -> Unit,
    navigateToChangeDeliveryAddressScreen: () -> Unit,
    navigateToChangePaymentMethodScreen: () -> Unit,
    navigateToOrderHistoryScreen: () -> Unit,
    navigateToWishlistScreen: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 24.dp)
            .verticalScroll(scrollState),
    ) {

        FullName(state = state, changeName = changeName)
        Email(state = state, changeEmail = changeEmail)
        Password(changePassword = changePassword)
        PaymentMethod(
            state = state,
            navigateToChangePaymentMethodScreen = navigateToChangePaymentMethodScreen
        )
        DeliveryAddress(
            state = state,
            navigateToChangeDeliveryAddressScreen = navigateToChangeDeliveryAddressScreen
        )

        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.order_history),
            actions = {
                Text(
                    modifier = Modifier.clickable(onClick = navigateToOrderHistoryScreen),
                    text = stringResource(R.string.open).uppercase(),
                    fontSize = 15.sp,
                    fontFamily = SourceSansProSemiBold,
                    lineHeight = 18.sp,
                    color = DarkViolet
                )
            }
        )

        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.wishlist),
            actions = {
                Text(
                    modifier = Modifier.clickable(onClick = navigateToWishlistScreen),
                    text = stringResource(R.string.open).uppercase(),
                    fontSize = 15.sp,
                    fontFamily = SourceSansProSemiBold,
                    lineHeight = 18.sp,
                    color = DarkViolet
                )
            }
        )

        Spacer(modifier = Modifier.height(48.dp))
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.log_out),
            onClick = {
                logOut.invoke().also { navigateToCategories.invoke() }
            }
        )
    }
}

@Composable
private fun FullName(
    state: AccountScreenState,
    changeName: (String, String) -> Unit
) {

    var showChangeNameDialog by remember { mutableStateOf(false) }

    Subtitle(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.full_name),
        actions = {
            Text(
                modifier = Modifier.clickable {
                    showChangeNameDialog = !showChangeNameDialog
                },
                text = stringResource(R.string.change),
                fontSize = 15.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 18.sp,
                color = DarkViolet
            )
        }
    )
    Spacer(modifier = Modifier.height(32.dp))
    TextWithIcon(
        text = "${state.firstName} ${state.lastName}",
        painter = painterResource(R.drawable.user)
    )
    if (showChangeNameDialog) {
        var newFirstName by remember { mutableStateOf("") }
        var newLastName by remember { mutableStateOf("") }
        Dialog(onDismissRequest = { showChangeNameDialog = !showChangeNameDialog }) {
            Surface(
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(31.dp)
                ) {
                    AppTextField(
                        label = stringResource(R.string.first_name),
                        value = newFirstName,
                        onValueChange = { newFirstName = it },
                        isError = newFirstName.isBlank() || newFirstName.length < 2,
                        supportingText = {
                            ErrorText(
                                modifier = Modifier.padding(bottom = 2.dp),
                                text = stringResource(
                                    if (newFirstName.length < 2) R.string.first_name_is_too_short
                                    else R.string.field_is_empty
                                )
                            )
                        }
                    )
                    AppTextField(
                        label = stringResource(R.string.last_name),
                        value = newLastName,
                        onValueChange = { newLastName = it },
                        isError = newLastName.isBlank() || newLastName.length < 2,
                        supportingText = {
                            ErrorText(
                                modifier = Modifier.padding(bottom = 2.dp),
                                text = stringResource(
                                    if (newLastName.length < 2) R.string.first_name_is_too_short
                                    else R.string.field_is_empty
                                )
                            )
                        }
                    )
                    AppTextButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.confirm)
                    ) {
                        if ((newFirstName.isNotBlank() && newFirstName.length > 1)
                            && (newLastName.isNotBlank() && newLastName.length > 1)
                        ) {
                            changeName.invoke(newFirstName, newLastName)
                            showChangeNameDialog = !showChangeNameDialog
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Email(
    state: AccountScreenState,
    changeEmail: (String) -> Unit
) {

    val showChangeEmailDialog = remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(48.dp))
    Subtitle(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.email),
        actions = {
            Text(
                modifier = Modifier.clickable {
                    showChangeEmailDialog.value = !showChangeEmailDialog.value
                },
                text = stringResource(R.string.change),
                fontSize = 15.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 18.sp,
                color = DarkViolet
            )
        }
    )
    Spacer(modifier = Modifier.height(32.dp))
    TextWithIcon(
        text = state.email,
        painter = painterResource(R.drawable.email)
    )
    if (showChangeEmailDialog.value) {
        var newEmail by remember { mutableStateOf("") }
        var invalidEmail by remember { mutableStateOf(false) }
        ChangeFieldDialog(
            label = stringResource(R.string.email),
            value = newEmail,
            onValueChange = {
                newEmail = it
                invalidEmail = !Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
            },
            show = showChangeEmailDialog,
            isError = invalidEmail || newEmail.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(
                        if (invalidEmail) R.string.invalid_email_address
                        else R.string.field_is_empty
                    ),
                )
            },
            onConfirmButtonClick = {
                if (!invalidEmail && newEmail.isNotBlank()) {
                    changeEmail.invoke(newEmail)
                }
            }
        )
    }
}

@Composable
private fun Password(
    changePassword: (String) -> Unit
) {
    val showChangePasswordDialog = remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(48.dp))
    Subtitle(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.password),
        actions = {
            Text(
                modifier = Modifier.clickable {
                    showChangePasswordDialog.value = !showChangePasswordDialog.value
                },
                text = stringResource(R.string.change),
                fontSize = 15.sp,
                fontFamily = SourceSansProSemiBold,
                lineHeight = 18.sp,
                color = DarkViolet
            )
        }
    )
    Spacer(modifier = Modifier.height(32.dp))
    TextWithIcon(
        text = "*****************",
        painter = painterResource(R.drawable.password)
    )
    if (showChangePasswordDialog.value) {
        var newPassword by remember { mutableStateOf("") }
        ChangeFieldDialog(
            label = stringResource(R.string.email),
            value = newPassword,
            onValueChange = { newPassword = it },
            show = showChangePasswordDialog,
            isError = newPassword.length < 3,
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.password_is_too_short),
                )
            },
            onConfirmButtonClick = {
                if (newPassword.length > 3) {
                    changePassword.invoke(newPassword)
                }
            }
        )
    }
}

@Composable
private fun PaymentMethod(
    state: AccountScreenState,
    navigateToChangePaymentMethodScreen: () -> Unit
) {
    Column() {
        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.payment_method),
            actions = {
                Text(
                    modifier = Modifier.clickable(onClick = navigateToChangePaymentMethodScreen),
                    text = stringResource(R.string.change),
                    fontSize = 15.sp,
                    fontFamily = SourceSansProSemiBold,
                    lineHeight = 18.sp,
                    color = DarkViolet
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        state.creditCard?.let {
            TextWithIcon(
                text = it.number,
                painter = painterResource(R.drawable.credit_card)
            )
        } ?: run {
            Text(
                text = stringResource(R.string.not_yet_determined),
                fontSize = 17.sp,
                fontFamily = SourceSansProRegular,
                lineHeight = 25.sp,
                color = Purple
            )
        }
    }
}

@Composable
private fun DeliveryAddress(
    state: AccountScreenState,
    navigateToChangeDeliveryAddressScreen: () -> Unit,
) {
    Column() {
        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.delivery_address),
            actions = {
                Text(
                    modifier = Modifier.clickable(onClick = navigateToChangeDeliveryAddressScreen),
                    text = stringResource(R.string.change),
                    fontSize = 15.sp,
                    fontFamily = SourceSansProSemiBold,
                    lineHeight = 18.sp,
                    color = DarkViolet
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        state.address?.let {
            TextWithIcon(
                text = it,
                painter = painterResource(R.drawable.house)
            )
        } ?: run {
            Text(
                text = stringResource(R.string.not_yet_determined),
                fontSize = 17.sp,
                fontFamily = SourceSansProRegular,
                lineHeight = 25.sp,
                color = Purple
            )
        }
    }
}


@Composable
private fun ChangeFieldDialog(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    show: MutableState<Boolean>,
    supportingText: @Composable () -> Unit,
    isError: Boolean,
    onConfirmButtonClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { show.value = !show.value }
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(31.dp)
            ) {
                AppTextField(
                    label = label,
                    value = value,
                    onValueChange = onValueChange,
                    isError = isError,
                    supportingText = supportingText
                )
                AppTextButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.confirm)
                ) {
                    onConfirmButtonClick.invoke()
                    show.value = !show.value
                }
            }
        }
    }

}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun AccountScreenPreview() {
    SeedsStoreTheme {
        AccountScreen(
            state = AccountScreenState(
                "Adam",
                "Smith",
                "me@gmail.com",
                com.calmperson.seedsstore.data.CreditCard(
                    "John Smith",
                    "4747  4747  4747  4747",
                    "07/21",
                    163
                ),
                "John Smith\n" +
                        "Cesu 31 k-2 5.st, SIA Chili\n" +
                        "Riga\n" +
                        "LV–1012\n" +
                        "Latvia\n",
                listOf(),
                listOf()
            ),
            changeName = { _, _ -> },
            changeEmail = { _ -> },
            changePassword = { _ -> },
            logOut = { },
            navigateToCategories = { },
            navigateToChangeDeliveryAddressScreen = { },
            navigateToChangePaymentMethodScreen = { },
            navigateToOrderHistoryScreen = { },
            navigateToWishlistScreen = { }
        )
    }
}