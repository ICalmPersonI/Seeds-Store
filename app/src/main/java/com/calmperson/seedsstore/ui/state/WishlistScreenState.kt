package com.calmperson.seedsstore.ui.state

import kotlinx.coroutines.flow.StateFlow


data class WishlistScreenState(val seeds: List<StateFlow<SeedState>>)
