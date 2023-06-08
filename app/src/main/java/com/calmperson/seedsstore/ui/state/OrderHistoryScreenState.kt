package com.calmperson.seedsstore.ui.state

import kotlinx.coroutines.flow.StateFlow


data class OrderHistoryScreenState(val seeds: List<StateFlow<SeedState>>)
