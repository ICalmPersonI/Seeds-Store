package com.calmperson.seedsstore.data.source.local.seed

import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.data.SeedCategory
import com.calmperson.seedsstore.data.source.local.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(private val database: AppDatabase) : SeedRepository {

    override var category: SeedCategory = SeedCategory.VEGETABLE
    override val seeds: Flow<List<Seed>> = database.seedDao().getAll()

    override suspend fun update(seed: Seed) {
        database.seedDao().update(seed)
    }

}