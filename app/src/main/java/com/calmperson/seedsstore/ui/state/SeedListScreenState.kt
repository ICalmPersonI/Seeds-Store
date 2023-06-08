package com.calmperson.seedsstore.ui.state

import com.calmperson.seedsstore.data.SeedCategory
import kotlinx.coroutines.flow.StateFlow

data class SeedListScreenState(
    val category: SeedCategory,
    val seeds: List<StateFlow<SeedState>>,
    val userCart: List<Long>,
    val userWishlist: List<Long>
)