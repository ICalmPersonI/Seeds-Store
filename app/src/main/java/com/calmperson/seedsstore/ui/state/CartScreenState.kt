package com.calmperson.seedsstore.ui.state

import kotlinx.coroutines.flow.StateFlow


data class CartScreenState(val seeds: List<StateFlow<SeedState>>)
