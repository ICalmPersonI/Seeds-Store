package com.calmperson.seedsstore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.Subtitle
import com.calmperson.seedsstore.ui.components.TextWithIcon
import com.calmperson.seedsstore.ui.state.CheckoutScreenState
import com.calmperson.seedsstore.ui.theme.DarkPurple
import com.calmperson.seedsstore.ui.theme.DarkViolet
import com.calmperson.seedsstore.ui.theme.LightViolet
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProBold
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular
import com.calmperson.seedsstore.ui.theme.SourceSansProSemiBold
import com.calmperson.seedsstore.ui.theme.Violet

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    state: CheckoutScreenState,
    navigateToChangePaymentMethodScreen: () -> Unit,
    navigateToChangeDeliveryAddressScreen: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {

    val scrollState = rememberScrollState(0)
    var isNonContactDelivery by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 24.dp)
            .verticalScroll(scrollState),
    ) {
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.payment_method),
            actions = {
                Text(
                    modifier = Modifier
                        .clickable(onClick = navigateToChangePaymentMethodScreen),
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
            text = state.creditCardNumber ?: stringResource(R.string.not_yet_determined),
            painter = painterResource(R.drawable.credit_card)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.delivery_address),
            actions = {
                Text(
                    modifier = Modifier
                        .clickable(onClick = navigateToChangeDeliveryAddressScreen),
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
            text = state.deliveryAddress ?: stringResource(R.string.not_yet_determined),
            painter = painterResource(R.drawable.house)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Subtitle(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.delivery_options),
            actions = { }
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextWithIcon(
            text = stringResource(R.string.i_will_pick_it_up_myself),
            painter = painterResource(R.drawable.walker_or_runner_sportive_stick_man)
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextWithIcon(
            text = stringResource(R.string.by_courier),
            painter = painterResource(R.drawable.motorcycle_delivery_single_box)
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextWithIcon(
            text = stringResource(R.string.by_drone),
            painter = painterResource(R.drawable.drone)
        )
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.non_contact_delivery),
                fontSize = 22.sp,
                fontFamily = SourceSansProBold,
                lineHeight = 22.sp
            )
            Switch(
                modifier = Modifier
                    .height(30.dp)
                    .align(Alignment.CenterVertically),
                checked = isNonContactDelivery,
                onCheckedChange = { isNonContactDelivery = it },
                thumbContent = {
                    Text(
                        text = stringResource(if (isNonContactDelivery) R.string.yes else R.string.no),
                        fontSize = 12.sp,
                        fontFamily = SourceSansProSemiBold,
                        lineHeight = 18.sp,
                        color = DarkViolet
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Violet,
                    uncheckedTrackColor = LightViolet,
                    disabledCheckedTrackColor = LightViolet,
                    checkedBorderColor = Color.White,
                    uncheckedBorderColor = Color.White,
                    disabledCheckedBorderColor = Color.White,
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    disabledCheckedThumbColor = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.confirm),
            onClick = onConfirmButtonClick
        )

    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun CheckoutScreenPreview() {
    SeedsStoreTheme {
        CheckoutScreen(
            state = CheckoutScreenState("**** **** **** 4747", "To home"),
            navigateToChangeDeliveryAddressScreen = { },
            navigateToChangePaymentMethodScreen = { },
            onConfirmButtonClick = { }
        )
    }
}
