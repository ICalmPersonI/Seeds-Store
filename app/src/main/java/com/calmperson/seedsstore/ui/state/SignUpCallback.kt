package com.calmperson.seedsstore.ui.state

import androidx.annotation.StringRes

data class SignUpCallback(
    val success: Boolean,
    @StringRes val message: Int
)