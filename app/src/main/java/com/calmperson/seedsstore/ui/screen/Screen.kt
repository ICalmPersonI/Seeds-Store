package com.calmperson.seedsstore.ui.screen

import androidx.annotation.DrawableRes
import com.calmperson.seedsstore.R

sealed class Screen(val route: String, @DrawableRes val iconResId: Int) {
    object Categories : Screen("categories", R.drawable.grid)
    object Cart : Screen("cart", R.drawable.shopping_cart)
    object Account : Screen("account", R.drawable.user)
    object SeedList : Screen("seedList/{id}", -1)
    object Seed : Screen("seed/{id}", -1)
    object Checkout : Screen("checkout", -1)
    object SignIn: Screen("signIn", -1)
    object SignUp: Screen("singUp", -1)
    object ChangeDeliveryAddress : Screen("changeDeliveryAddress", -1)
    object ChangePaymentMethod : Screen("changePaymentMethod", -1)
    object Wishlist: Screen("wishlist", -1)
    object OrderHistory: Screen("orderHistory", -1)
}