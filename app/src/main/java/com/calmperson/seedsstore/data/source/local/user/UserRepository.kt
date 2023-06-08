package com.calmperson.seedsstore.data.source.local.user

import com.calmperson.seedsstore.data.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    fun findByEmail(email: String): User?
    fun findById(id: Long): User?
    suspend fun insert(user: User): Long
    suspend fun update(user: User)

}