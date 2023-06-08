package com.calmperson.seedsstore.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calmperson.seedsstore.ui.components.BottomAppBar
import com.calmperson.seedsstore.ui.components.TopAppBar
import com.calmperson.seedsstore.ui.screen.CartScreen
import com.calmperson.seedsstore.ui.screen.CategoriesScreen
import com.calmperson.seedsstore.ui.screen.ChangeDeliveryAddressScreen
import com.calmperson.seedsstore.ui.screen.ChangePaymentMethodScreen
import com.calmperson.seedsstore.ui.screen.CheckoutScreen
import com.calmperson.seedsstore.ui.screen.OrderHistoryScreen
import com.calmperson.seedsstore.ui.screen.Screen
import com.calmperson.seedsstore.ui.screen.SeedListScreen
import com.calmperson.seedsstore.ui.screen.SeedScreen
import com.calmperson.seedsstore.ui.screen.WishListScreen
import com.calmperson.seedsstore.ui.screen.account.AccountScreen
import com.calmperson.seedsstore.ui.screen.account.SignInScreen
import com.calmperson.seedsstore.ui.screen.account.SignUpScreen
import com.calmperson.seedsstore.ui.state.AccountScreenState
import com.calmperson.seedsstore.ui.state.CartScreenState
import com.calmperson.seedsstore.ui.state.CheckoutScreenState
import com.calmperson.seedsstore.ui.state.OrderHistoryScreenState
import com.calmperson.seedsstore.ui.state.SeedListScreenState
import com.calmperson.seedsstore.ui.state.SeedScreenState
import com.calmperson.seedsstore.ui.state.SignInCallback
import com.calmperson.seedsstore.ui.state.SignUpCallback
import com.calmperson.seedsstore.ui.state.WishlistScreenState
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Root(
    seedListScreenStateFlow: StateFlow<SeedListScreenState?>,
    seedScreenStateFlow: StateFlow<SeedScreenState?>,
    accountScreenStateFlow: StateFlow<AccountScreenState?>,
    cartScreenStateFlow: StateFlow<CartScreenState?>,
    orderHistoryScreenStateFlow: StateFlow<OrderHistoryScreenState?>,
    wishlistScreenStateFlow: StateFlow<WishlistScreenState?>,
    checkoutScreenStateFlow: StateFlow<CheckoutScreenState?>,
    updateSeedListScreenState: (Int) -> Unit,
    updateSeedScreenState: (Long) -> Unit,
    signIn: (String, String, Boolean) -> StateFlow<SignInCallback?>,
    signUp: (String, String, String, String) -> StateFlow<SignUpCallback?>,
    logOut: () -> Unit,
    changeName: (String, String) -> Unit,
    changeEmail: (String) -> Unit,
    changePassword: (String) -> Unit,
    changePaymentMethod: (String, String, String, Int) -> Unit,
    changeDeliveryAddress: (String, String, String, String, String) -> Unit,
    onAddToCartButtonClick: (Long) -> Unit,
    onAddToWishlistButtonClick: (Long) -> Unit,
    onBuyButtonClick: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(Screen.Categories, Screen.Cart, Screen.Account)

    val seedListScreenState = seedListScreenStateFlow.collectAsState()
    val seedScreenState = seedScreenStateFlow.collectAsState()
    val accountScreenState = accountScreenStateFlow.collectAsState()
    val cartScreenState = cartScreenStateFlow.collectAsState()
    val orderHistoryScreenState = orderHistoryScreenStateFlow.collectAsState()
    val wishlistScreenState = wishlistScreenStateFlow.collectAsState()
    val checkoutScreenState = checkoutScreenStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(96.dp),
                navController = navController
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                navController = navController,
                currentDestination = currentDestination,
                screens = screens
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.Categories.route
        ) {

            composable(Screen.Categories.route) {
                CategoriesScreen(navController = navController)
            }

            composable(
                route = Screen.SeedList.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType})
            ) { backStack ->
                backStack.arguments?.getInt("id")?.let { id ->
                    updateSeedListScreenState(id)
                    seedListScreenState.value?.let { state ->
                        SeedListScreen(
                            seeds = state.seeds,
                            labelResId = state.category.nameStrId,
                            onAddToCartButtonClick = onAddToCartButtonClick,
                            onAddToWishlistButtonClick = onAddToWishlistButtonClick,
                            navigateToSeed = { seedId -> navController.navigate("seed/$seedId") },
                            isUserLogged = accountScreenState.value != null
                        )
                    }
                }
            }

            composable(
                route = Screen.Seed.route,
                arguments = listOf(navArgument("id") {type = NavType.LongType})
            ) { backStack ->
                backStack.arguments?.getLong("id")?.let { id ->
                    updateSeedScreenState(id)
                    seedScreenState.value?.let { state ->
                        SeedScreen(
                            state = state,
                            onAddToCartButtonClick = onAddToCartButtonClick,
                            onAddToWishlistButtonClick = onAddToWishlistButtonClick,
                            isUserLogged = accountScreenState.value != null
                        )
                    }
                }
            }

            composable(Screen.Cart.route) {
                if (accountScreenState.value != null) {
                    cartScreenState.value?.let { state ->
                        CartScreen(
                            state = state,
                            onAddToCartButtonClick = onAddToCartButtonClick,
                            onAddToWishlistButtonClick = onAddToWishlistButtonClick,
                            navigateToSeed = { seedId -> navController.navigate("seed/$seedId") },
                            navigateToCheckoutScreen = { navController.navigate(Screen.Checkout.route) },
                            onBuyButtonClick = onBuyButtonClick
                        )
                    }
                } else {
                    LaunchedEffect(navController) {
                        navController.navigate(Screen.SignIn.route)
                    }
                }
            }

            composable(Screen.Account.route) {
                if (accountScreenState.value != null) {
                    AccountScreen(
                        state = accountScreenState.value!!,
                        changeName = changeName,
                        changeEmail = changeEmail,
                        changePassword = changePassword,
                        logOut = logOut,
                        navigateToCategories = { navController.navigate(Screen.Categories.route) },
                        navigateToChangePaymentMethodScreen = { navController.navigate(Screen.ChangePaymentMethod.route) },
                        navigateToChangeDeliveryAddressScreen = { navController.navigate(Screen.ChangeDeliveryAddress.route) },
                        navigateToOrderHistoryScreen = { navController.navigate(Screen.OrderHistory.route) },
                        navigateToWishlistScreen = { navController.navigate(Screen.Wishlist.route) }
                    )
                } else {
                    LaunchedEffect(navController) {
                        navController.navigate(Screen.SignIn.route)
                    }
                }
            }

            composable(Screen.SignIn.route) {
                SignInScreen(
                    signIn = signIn,
                    navigateToAccountScreen = {
                        navController.navigate(Screen.Account.route) {
                            popUpTo(Screen.SignIn.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSignUpScreen = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )
            }

            composable(Screen.SignUp.route) {
                SignUpScreen(
                    signUp = signUp,
                    navigateToAccountScreen = {
                        navController.navigate(Screen.Account.route) {
                            popUpTo(Screen.SignUp.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSignInScreen = {
                        navController.navigate(Screen.SignIn.route)
                    }
                )
            }

            composable(Screen.Checkout.route) {
                checkoutScreenState.value?.let { state ->
                    CheckoutScreen(
                        state = state,
                        navigateToChangePaymentMethodScreen = { navController.navigate(Screen.ChangePaymentMethod.route) },
                        navigateToChangeDeliveryAddressScreen = { navController.navigate(Screen.ChangeDeliveryAddress.route) },
                        onConfirmButtonClick = {

                            navController.popBackStack()
                        }
                    )
                }
            }

            composable(Screen.ChangeDeliveryAddress.route) {
                ChangeDeliveryAddressScreen(
                    changeDeliveryAddress = changeDeliveryAddress,
                    navigateToBack = { navController.popBackStack() }
                )
            }

            composable(Screen.ChangePaymentMethod.route) {
                ChangePaymentMethodScreen(
                    changePaymentMethod = changePaymentMethod,
                    navigateToBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Wishlist.route,) {
                wishlistScreenState.value?.let { state ->
                    WishListScreen(
                        state = state,
                        onAddToCartButtonClick = onAddToCartButtonClick,
                        onAddToWishlistButtonClick = onAddToWishlistButtonClick,
                        navigateToSeed = { seedId -> navController.navigate("seed/$seedId") }
                    )
                }
            }

            composable(Screen.OrderHistory.route,) {
                orderHistoryScreenState.value?.let { state ->
                    OrderHistoryScreen(
                        state = state,
                        onAddToCartButtonClick = onAddToCartButtonClick,
                        onAddToWishlistButtonClick = onAddToWishlistButtonClick,
                        navigateToSeed = { seedId -> navController.navigate("seed/$seedId") }
                    )
                }
            }
        }
    }
}