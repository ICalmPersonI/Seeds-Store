package com.calmperson.seedsstore.ui.state

import com.calmperson.seedsstore.data.Seed

data class SeedState(
    val seed: Seed,
    val isAvailable: Boolean,
    val isInWishlist: Boolean,
    val isInCart: Boolean
)
