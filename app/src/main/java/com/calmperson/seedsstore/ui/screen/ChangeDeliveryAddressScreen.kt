package com.calmperson.seedsstore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.AppTextField
import com.calmperson.seedsstore.ui.components.ErrorText
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme

@Composable
fun ChangeDeliveryAddressScreen(
    modifier: Modifier = Modifier,
    changeDeliveryAddress: (String, String, String, String, String) -> Unit,
    navigateToBack: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        AppTextField(
            label = stringResource(R.string.full_name),
            value = fullName,
            onValueChange = { fullName = it },
            isError = fullName.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        Spacer(modifier = Modifier.height(23.dp))
        AppTextField(
            label = stringResource(R.string.address),
            value = address,
            onValueChange = { address = it },
            isError = address.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        Spacer(modifier = Modifier.height(23.dp))
        AppTextField(
            label = stringResource(R.string.city),
            value = city,
            onValueChange = { city = it },
            isError = city.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        Spacer(modifier = Modifier.height(23.dp))
        AppTextField(
            label = stringResource(R.string.postal_code),
            value = postalCode,
            onValueChange = { postalCode = it },
            isError = postalCode.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        Spacer(modifier = Modifier.height(23.dp))
        AppTextField(
            label = stringResource(R.string.country),
            value = country,
            onValueChange = { country = it },
            isError = country.isBlank(),
            supportingText = {
                ErrorText(
                    modifier = Modifier.padding(bottom = 2.dp),
                    text = stringResource(R.string.field_is_empty),
                )
            }
        )
        Spacer(modifier = Modifier.height(55.dp))
        AppTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.use_this_address).uppercase(),
            onClick = {
                if (fullName.isNotBlank() && address.isNotBlank()
                    && city.isNotBlank() && postalCode.isNotBlank()
                    && country.isNotBlank()) {
                    changeDeliveryAddress(fullName, address, city, postalCode, country)
                    navigateToBack.invoke()
                }
            }
        )
        Spacer(modifier = Modifier.height(55.dp))
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun ChangeDeliveryAddressScreenPreview() {
    SeedsStoreTheme {
        ChangeDeliveryAddressScreen(
            changeDeliveryAddress = {_, _, _, _, _ -> },
            navigateToBack = { }
        )
    }
}