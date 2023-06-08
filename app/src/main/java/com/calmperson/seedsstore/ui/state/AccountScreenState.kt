package com.calmperson.seedsstore.ui.state

import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.data.Seed
import java.net.Inet4Address

data class AccountScreenState(
    val firstName: String,
    val lastName: String,
    val email: String,
    val creditCard: CreditCard?,
    val address: String?,
    val orderHistory: List<Seed>,
    val wishList: List<Seed>,
)
