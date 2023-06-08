package com.calmperson.seedsstore.data

import androidx.annotation.DrawableRes
import com.calmperson.seedsstore.R

data class CreditCard(val name: String, val number: String, val expireDate: String, val cvc: Int) {
    enum class Vendor(val pattern: Regex, @DrawableRes val iconId: Int) {
        MASTER_CARD(Regex("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$"), R.drawable.mastercard),
        VISA(Regex("^4[0-9]{12}(?:[0-9]{3})?$"), R.drawable.visa)
    }
}
