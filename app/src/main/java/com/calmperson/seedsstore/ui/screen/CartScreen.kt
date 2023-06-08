package com.calmperson.seedsstore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.components.SearchField
import com.calmperson.seedsstore.ui.components.SeedContainer
import com.calmperson.seedsstore.ui.state.CartScreenState
import com.calmperson.seedsstore.ui.theme.RobotoBold
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    state: CartScreenState,
    onAddToCartButtonClick: (Long) -> Unit,
    onAddToWishlistButtonClick: (Long) -> Unit,
    navigateToSeed: (Long) -> Unit,
    navigateToCheckoutScreen: () -> Unit,
    onBuyButtonClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        SeedListScreen(
            modifier = Modifier.weight(1f),
            labelResId = R.string.cart,
            seeds = state.seeds,
            onAddToCartButtonClick = onAddToCartButtonClick,
            onAddToWishlistButtonClick = onAddToWishlistButtonClick,
            navigateToSeed = navigateToSeed,
            isUserLogged = true
        )

        AppTextButton(
            modifier = Modifier.weight(0.15f)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.buy),
            onClick = {
                onBuyButtonClick.invoke()
                navigateToCheckoutScreen.invoke()
            }
        )

    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 450,
    heightDp = 900
)
private fun CartScreenPreview() {
    SeedsStoreTheme {
        CartScreen(
            state = CartScreenState(emptyList()),
            onAddToCartButtonClick = { },
            onAddToWishlistButtonClick = { },
            navigateToSeed = { },
            navigateToCheckoutScreen = { },
            onBuyButtonClick = { }
        )
    }
}