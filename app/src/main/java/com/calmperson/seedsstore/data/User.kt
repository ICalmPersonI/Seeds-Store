package com.calmperson.seedsstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var creditCard: CreditCard?,
    var address: String?,
    val orderHistory: MutableList<Long>,
    val wishList: MutableList<Long>,
    val cart: MutableList<Long>,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)