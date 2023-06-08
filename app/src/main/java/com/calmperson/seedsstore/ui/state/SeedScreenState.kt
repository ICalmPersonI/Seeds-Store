package com.calmperson.seedsstore.ui.state

import com.calmperson.seedsstore.data.Seed

data class SeedScreenState(
    val seed: Seed,
    val isAvailable: Boolean,
    val isInWishlist: Boolean,
    val isInCart: Boolean
)
