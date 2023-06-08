package com.calmperson.seedsstore.data.source.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.calmperson.seedsstore.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE :email = user.email")
    fun findByEmail(email: String): User?

    @Query("SELECT * FROM user WHERE :id = user.id")
    fun findById(id: Long): User?

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

}