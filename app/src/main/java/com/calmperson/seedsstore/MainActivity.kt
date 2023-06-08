package com.calmperson.seedsstore

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.ui.Root
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        val sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE)

        //This is not the correct way to store a password, I understand that.
        with(sharedPreferences) {
            val email = this.getString(getString(R.string.remember_me_email_key), null)
            val password = this.getString(getString(R.string.remember_me_email_key), null)
            if (password != null && email != null) {
                viewModel.signIn(email, password, rememberMe = { })
            }
        }

        setContent {
            SeedsStoreTheme {
                Root(
                    seedListScreenStateFlow = viewModel.categoryScreenState,
                    seedScreenStateFlow = viewModel.seedScreenState,
                    accountScreenStateFlow = viewModel.accountScreenState,
                    cartScreenStateFlow = viewModel.cartScreenState,
                    wishlistScreenStateFlow = viewModel.wishlistScreenState,
                    orderHistoryScreenStateFlow = viewModel.orderHistoryScreenState,
                    checkoutScreenStateFlow = viewModel.checkoutScreenState,
                    updateSeedListScreenState = { id -> viewModel.updateSeedListScreenState(id) },
                    updateSeedScreenState = { id -> viewModel.updateSeedScreenState(id) },
                    signIn = { email, password, rememberMe ->
                        viewModel.signIn(
                            email = email,
                            password = password,
                            rememberMe = {
                                if (rememberMe) {
                                    with(sharedPreferences.edit()) {
                                        putString(getString(R.string.remember_me_email_key), email)
                                        putString(getString(R.string.remember_me_password_key), password)
                                        commit()
                                    }
                                }
                            }
                        )
                    },
                    signUp = { fistName, lastName, email, password ->
                        viewModel.signUp(fistName, lastName, email, password)
                    },
                    logOut = { viewModel.logOut() },
                    changeName = { firstName, lastName -> viewModel.updateUserData(firstName = firstName, lastName = lastName) },
                    changeEmail = { email -> viewModel.updateUserData(email = email)},
                    changePassword = { password -> viewModel.updateUserData(password = password)},
                    changePaymentMethod = { nameOnCard, number, expireDate, cvc ->
                        val newCreditCard = CreditCard(nameOnCard, number, expireDate, cvc)
                        viewModel.updateUserData(creditCard = newCreditCard)
                    },
                    changeDeliveryAddress = { fullName, address, city, postalCode, country ->
                        val newAddress = "$fullName\n$address\n$city\n$postalCode\n$country"
                        viewModel.updateUserData(address = newAddress)
                    },
                    onAddToCartButtonClick = { id -> viewModel.addToCart(id) },
                    onAddToWishlistButtonClick = { id -> viewModel.addToWishList(id) },
                    onBuyButtonClick = { viewModel.buy() }
                )
            }
        }
    }
}