package com.calmperson.seedsstore.ui.state

import androidx.annotation.StringRes

data class SignInCallback(
    val success: Boolean,
    @StringRes val message: Int
)
