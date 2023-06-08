package com.calmperson.seedsstore.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.state.OrderHistoryScreenState

@Composable
fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    state: OrderHistoryScreenState,
    onAddToCartButtonClick: (Long) -> Unit,
    onAddToWishlistButtonClick: (Long) -> Unit,
    navigateToSeed: (Long) -> Unit,
) {
    SeedListScreen(
        modifier = modifier,
        labelResId = R.string.order_history,
        seeds = state.seeds,
        onAddToCartButtonClick = onAddToCartButtonClick,
        onAddToWishlistButtonClick = onAddToWishlistButtonClick,
        navigateToSeed = navigateToSeed,
        isUserLogged = true
    )
}