package com.calmperson.seedsstore.data.source.local.seed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.calmperson.seedsstore.data.Seed
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {

    @Query("SELECT * FROM seed")
    fun getAll(): Flow<List<Seed>>

    @Insert
    suspend fun insertAll(vararg seed: Seed)

    @Insert
    suspend fun insert(seed: Seed)

    @Update
    suspend fun update(seed: Seed)

}