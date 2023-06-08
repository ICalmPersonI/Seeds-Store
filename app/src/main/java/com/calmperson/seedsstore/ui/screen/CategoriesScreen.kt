package com.calmperson.seedsstore.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.data.SeedCategory
import com.calmperson.seedsstore.ui.components.SearchField
import com.calmperson.seedsstore.ui.theme.RobotoBold
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme


private data class Category(val categoryId: Int, val name: String, val imageName: String)

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var searchFiledValue by remember { mutableStateOf("") }
    val categories = SeedCategory.values().map { Category(it.id, stringResource(it.nameStrId), it.imagePath) }

    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            text = stringResource(R.string.categories),
            fontSize = 30.sp,
            fontFamily = RobotoBold,
            lineHeight = 41.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(27.dp))
        SearchField(
            modifier = Modifier.fillMaxWidth(),
            value = searchFiledValue,
            onValueChange = { value -> searchFiledValue = value }
        )
        Spacer(modifier = Modifier.height(42.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                if (searchFiledValue.isNotEmpty()) categories.filter {
                    it.name.lowercase().contains(searchFiledValue.lowercase())
                } else categories
            ) { category ->
                Item(
                    modifier = Modifier,
                    category = category,
                    onClick = {
                        navController.navigate("seedList/${category.categoryId}")
                    }
                )
            }

        }

    }

}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(180.dp, 217.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(),
                model = "file:///android_asset/category/${category.imageName}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = category.name,
                fontSize = 20.sp,
                fontFamily = RobotoBold,
                lineHeight = 21.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun CategoriesScreenPreview() {
    SeedsStoreTheme {
        CategoriesScreen(
            navController = rememberNavController()
        )
    }
}