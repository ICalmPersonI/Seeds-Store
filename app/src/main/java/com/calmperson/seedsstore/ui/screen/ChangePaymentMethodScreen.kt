package com.calmperson.seedsstore.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.AppTextField
import com.calmperson.seedsstore.ui.components.CreditCard
import com.calmperson.seedsstore.ui.components.ErrorText
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProBold
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ChangePaymentMethodScreen(
    modifier: Modifier = Modifier,
    changePaymentMethod: (String, String, String, Int) -> Unit,
    navigateToBack: () -> Unit,
) {
    var cardNumber by remember { mutableStateOf("") }
    var nameOnCard by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cardVendor by remember { mutableStateOf<CreditCard.Vendor?>(null) }
    var invalidExpiryDate by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(23.dp)
    ) {
        Text(
            text = stringResource(R.string.credit_debit_card),
            fontSize = 30.sp,
            fontFamily = SourceSansProBold,
            lineHeight = 41.sp
        )
        CreditCard(
            modifier = Modifier
                .height(240.dp)
                .widthIn(max = 375.dp)
                .align(Alignment.CenterHorizontally),
            cardTypeIcon = { modifier ->
                cardVendor?.let {
                    Icon(
                        modifier = modifier.size(32.dp, 40.dp),
                        painter = painterResource(cardVendor!!.iconId),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            },
            number = cardNumber,
            name = nameOnCard,
            date = expiryDate
        )
        AppTextField(
            label = stringResource(R.string.name_on_card),
            value = nameOnCard,
            onValueChange = { nameOnCard = it },
            isError = nameOnCard.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        AppTextField(
            label = stringResource(R.string.card_number),
            value = cardNumber,
            onValueChange = {
                if (it.length <= 18) {
                    cardNumber = it
                    cardVendor = creditCardValidator(cardNumber)
                }
            },
            trailingIcon = {
                cardVendor?.let {
                    Icon(
                        modifier = Modifier
                            .padding(top = 13.dp, bottom = 15.dp, end = 16.dp)
                            .size(20.dp, 30.dp),
                        painter = painterResource(cardVendor!!.iconId),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            },
            isError = cardNumber.isBlank() || cardVendor == null,
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(
                        if (cardNumber.isBlank()) R.string.field_is_empty
                        else R.string.wrong_card_number
                    ),
                )
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AppTextField(
                modifier = Modifier.weight(1f).height(if (invalidExpiryDate) 100.dp else 80.dp),
                label = stringResource(R.string.expiry_date),
                value = expiryDate,
                onValueChange = {
                    if (it.length <= 5) {
                        expiryDate = it
                        invalidExpiryDate = false
                    }
                },
                isError = invalidExpiryDate,
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(R.string.wrong_expiry_date),
                    )
                },
                placeholder = {
                    Text("MM/YY")
                }

            )
            Spacer(modifier = Modifier.width(22.dp))
            AppTextField(
                modifier = Modifier.weight(1f).height(if (cvc.isBlank()) 100.dp else 80.dp),
                label = stringResource(R.string.cvc),
                value = cvc,
                onValueChange = { if (it.length <= 3) cvc = it },
                /*
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 9.dp, end = 12.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = Color.Red,
                                    topLeft = this.center.plus(Offset(0f, 3f)),
                                    size = Size(25f, 15f),
                                    cornerRadius = CornerRadius(10f, 10f),
                                    style = Stroke(width = 2.dp.toPx())
                                )
                            },
                        painter = painterResource(R.drawable.credit_card),
                        contentDescription = null,
                        tint = LightViolet
                    )
                },
                 */
                isError = cvc.isBlank(),
                supportingText = {
                    ErrorText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = stringResource(R.string.field_is_empty),
                    )
                }
            )
        }
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.use_this_card).uppercase(),
            onClick = {
                invalidExpiryDate = !checkExpiryDate(expiryDate)
                if (nameOnCard.isNotBlank()
                    && (cardVendor != null && cardNumber.isNotBlank())
                    && !invalidExpiryDate
                ) {
                    changePaymentMethod.invoke(nameOnCard, cardNumber, expiryDate, cvc.toInt())
                    navigateToBack.invoke()
                }
            }
        )

    }
}

private fun creditCardValidator(number: String): CreditCard.Vendor? {
    return when {
        CreditCard.Vendor.MASTER_CARD.pattern.matches(number) -> CreditCard.Vendor.MASTER_CARD
        CreditCard.Vendor.VISA.pattern.matches(number) -> CreditCard.Vendor.VISA
        else -> null
    }
}

private fun checkExpiryDate(date: String): Boolean {
    return try {
        val expiryDate = SimpleDateFormat("MM/yy", Locale.getDefault()).parse(date)
        Calendar.getInstance().time.before(expiryDate)
    } catch(e: ParseException) {
        false
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 800
)
private fun ChangePaymentMethodScreenPreview() {
    SeedsStoreTheme {
        ChangePaymentMethodScreen(
            changePaymentMethod = { _, _, _, _ -> },
            navigateToBack = { }
        )
    }
}