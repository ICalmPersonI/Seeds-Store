package com.calmperson.seedsstore.data

import androidx.annotation.StringRes
import com.calmperson.seedsstore.R

enum class SeedCategory(val id: Int, @StringRes val nameStrId: Int, val imagePath: String) {
    VEGETABLE(0, R.string.vegetable_seeds, "vegetables.jpg"),
    FRUIT(1, R.string.fruit_seeds, "fruits.jpg"),
    MEDICINAL_HERB(2, R.string.medicinal_herb_seeds, "medicinal_herb.jpg"),
    CLIMBING_PLANTS(3, R.string.climbing_plants_seeds, "climbing.jpg"),
    TREES_BONSAI(4, R.string.trees_bonsai_seeds, "bonsai.jpg"),
    PALM(5, R.string.palm_seeds, "palm.jpg"),
    TOBACCO(6, R.string.tobacco_seeds, "tobacco.jpg"),
    CACTUS(7, R.string.cactus_seeds, "cactus.jpg"),
    FLOWER(8, R.string.flower_seeds, "flowers.jpg"),
    BANANA(9, R.string.banana_seeds, "bananas.jpg"),
    BAMBOO(10, R.string.bamboo_seeds, "bamboo.jpg");

    companion object {
        infix fun from(id: Int): SeedCategory? = values().firstOrNull { it.id == id }
    }

}