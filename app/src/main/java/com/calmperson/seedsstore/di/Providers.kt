package com.calmperson.seedsstore.di

import android.content.Context
import androidx.room.Room
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.data.SeedCategory
import com.calmperson.seedsstore.data.source.local.AppDatabase
import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.data.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Providers {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "local_source"
        ).build().also { it.fillDB(context) }
    }

    private fun AppDatabase.fillDB(context: Context) {
        var json: String?
        context.resources.openRawResource(R.raw.database_data).use { stream ->
            val size = stream.available()
            val buffer = ByteArray(size).also { stream.read(it) }
            json = String(buffer, Charset.defaultCharset())
        }

        CoroutineScope(Dispatchers.IO).launch {
            json?.let {
                if (seedDao().getAll().first().isEmpty()) {
                    val seeds = JSONObject(it).getJSONArray("seeds")
                    for (i in 0 until seeds.length()) {
                        val jsonObject = seeds.getJSONObject(i)
                        val seed = Seed(
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("image"),
                            jsonObject.getDouble("price"),
                            jsonObject.getInt("amount"),
                            jsonObject.getJSONArray("category").toSeedCategoryList()
                        )
                        seedDao().insert(seed)
                    }
                }
                if (userDao().getAll().first().isEmpty()) {
                    val users = JSONObject(it).getJSONArray("users")
                    for (i in 0 until users.length()) {
                        val jsonObject = users.getJSONObject(i)
                        val user = User(
                            jsonObject.getString("firstName"),
                            jsonObject.getString("lastName"),
                            jsonObject.getString("email"),
                            jsonObject.getString("password"),
                            CreditCard("John Smith", "4747  4747  4747  4747", "07/21", 163),
                            "John Smith\n" +
                                    "Cesu 31 k-2 5.st, SIA Chili\n" +
                                    "Riga\n" +
                                    "LV–1012\n" +
                                    "Latvia\n",
                            mutableListOf(),
                            mutableListOf(),
                            mutableListOf()
                        )
                        userDao().insert(user)
                    }
                }
            }
        }
    }

    private fun JSONArray.toSeedCategoryList(): List<SeedCategory> {
        val list = mutableListOf<SeedCategory>()
        for (i in 0 until this.length()) {
            SeedCategory.from(this.getInt(i))?.let { list.add(it) }
        }
        return list
    }
}