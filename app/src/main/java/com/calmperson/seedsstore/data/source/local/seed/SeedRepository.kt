package com.calmperson.seedsstore.data.source.local.seed

import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.data.SeedCategory
import kotlinx.coroutines.flow.Flow


interface SeedRepository {
    val seeds: Flow<List<Seed>>
    var category: SeedCategory

    suspend fun update(seed: Seed)
}