package com.calmperson.seedsstore.di

import com.calmperson.seedsstore.data.source.local.seed.SeedRepository
import com.calmperson.seedsstore.data.source.local.seed.SeedRepositoryImpl
import com.calmperson.seedsstore.data.source.local.user.UserRepository
import com.calmperson.seedsstore.data.source.local.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Binds {

    @Binds
    abstract fun seedRepository(seedRepository: SeedRepositoryImpl): SeedRepository

    @Binds
    abstract fun userRepository(userRepository: UserRepositoryImpl): UserRepository

}