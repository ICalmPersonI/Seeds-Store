package com.calmperson.seedsstore.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.data.source.local.seed.SeedDao
import com.calmperson.seedsstore.data.User
import com.calmperson.seedsstore.data.source.local.user.UserDao

@TypeConverters(value = [TypeConverter::class])
@Database(entities = [User::class, Seed::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun seedDao(): SeedDao

}