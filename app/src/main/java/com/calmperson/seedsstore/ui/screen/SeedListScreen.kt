package com.calmperson.seedsstore.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.components.SearchField
import com.calmperson.seedsstore.ui.components.SeedContainer
import com.calmperson.seedsstore.ui.state.SeedState
import com.calmperson.seedsstore.ui.theme.RobotoBold
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SeedListScreen(
    modifier: Modifier = Modifier,
    @StringRes labelResId: Int,
    seeds: List<StateFlow<SeedState>>,
    onAddToCartButtonClick: (Long) -> Unit,
    onAddToWishlistButtonClick: (Long) -> Unit,
    navigateToSeed: (Long) -> Unit,
    isUserLogged: Boolean
) {
    var searchFiledValue by remember { mutableStateOf("") }
    val seedStates = seeds.map { it.collectAsState() }

    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(labelResId),
            fontSize = 30.sp,
            fontFamily = RobotoBold
        )
        Spacer(modifier = Modifier.height(27.dp))
        SearchField(
            modifier = Modifier.fillMaxWidth(),
            value = searchFiledValue,
            onValueChange = { value -> searchFiledValue = value }
        )
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = if (searchFiledValue.isNotEmpty()) seedStates.filter {
                    it.value.seed.name.lowercase().contains(searchFiledValue.lowercase())
                }
                else seedStates
            ) { seedState ->
                if(seedState.value.isAvailable) {
                    SeedContainer(
                        modifier = Modifier.height(160.dp),
                        state = seedState,
                        onClick = {
                            navigateToSeed.invoke(seedState.value.seed.id)
                        },
                        addToCart = onAddToCartButtonClick,
                        addToWishList = onAddToWishlistButtonClick,
                        isUserLogged = isUserLogged
                    )
                }
            }
        }
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun CategoryScreenPreview() {
    SeedsStoreTheme {
        SeedListScreen(
            seeds = emptyList(),
            labelResId = R.string.bamboo_seeds,
            navigateToSeed = { },
            onAddToCartButtonClick = { },
            onAddToWishlistButtonClick = { },
            isUserLogged = true
        )
    }
}