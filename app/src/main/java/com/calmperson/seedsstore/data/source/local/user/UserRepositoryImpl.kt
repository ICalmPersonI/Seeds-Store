package com.calmperson.seedsstore.data.source.local.user

import com.calmperson.seedsstore.data.User
import com.calmperson.seedsstore.data.source.local.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val database: AppDatabase) : UserRepository {
    override fun findByEmail(email: String): User? {
        return database.userDao().findByEmail(email)
    }

    override fun findById(id: Long): User? {
        return database.userDao().findById(id)
    }

    override suspend fun insert(user: User): Long {
        return database.userDao().insert(user)
    }

    override suspend fun update(user: User) {
        database.userDao().update(user)
    }

}