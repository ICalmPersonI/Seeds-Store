package com.calmperson.seedsstore.data.source.local

import androidx.room.TypeConverter
import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.data.SeedCategory
import org.json.JSONArray
import org.json.JSONObject

class TypeConverter {
    @TypeConverter
    fun seedCategoryListToString(list: List<SeedCategory>): String {
        return JSONArray(list.map { JSONObject(mapOf("id" to it.id)) }).toString()
    }

    @TypeConverter
    fun seedCategoryListAsStringToList(string: String): List<SeedCategory> {
        val list = mutableListOf<SeedCategory>()
        val json = JSONArray(string)
        for (i in 0 until json.length()) {
            val category: SeedCategory? = SeedCategory from json.getJSONObject(i).getInt("id")
            category?.let { list.add(it) }
        }
        return list
    }

    @TypeConverter
    fun longListToString(list: List<Long>): String {
        return JSONArray(list).toString()
    }

    @TypeConverter
    fun integerListAsStringToList(string: String): List<Long> {
        val list = mutableListOf<Long>()
        val json = JSONArray(string)
        for (i in 0 until json.length()) {
            list.add(json.getLong(i))
        }
        return list
    }

    @TypeConverter
    fun creditCardToString(card: CreditCard?): String {
        if (card == null) return ""
        return JSONObject(
            "{ " +
                    "\"name\":\"${card.name}\"," +
                    " \"number\":\"${card.name}\"," +
                    " \"expireDate\":\"${card.expireDate}\"," +
                    "\"cvc\":${card.cvc}" +
                    "}"
        ).toString()
    }

    @TypeConverter
    fun creditCardAsStringToObject(string: String): CreditCard? {
        if (string.isEmpty()) return null
        val json = JSONObject(string)
        return CreditCard(
            name = json.getString("name"),
            number = json.getString("number"),
            expireDate = json.getString("expireDate"),
            cvc = json.getInt("cvc")
        )
    }
}