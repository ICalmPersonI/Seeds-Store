package com.calmperson.seedsstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calmperson.seedsstore.data.SeedCategory

@Entity
data class Seed(
    val name: String,
    val description: String,
    val image: String,
    val price: Double, // for 5 seeds | euro
    var amount: Int,
    val categories: List<SeedCategory>,
    @PrimaryKey(autoGenerate = true) var id: Long = 0

)
