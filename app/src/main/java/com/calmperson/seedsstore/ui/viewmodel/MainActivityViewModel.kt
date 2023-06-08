package com.calmperson.seedsstore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.data.CreditCard
import com.calmperson.seedsstore.data.SeedCategory
import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.data.source.local.seed.SeedRepository
import com.calmperson.seedsstore.data.User
import com.calmperson.seedsstore.data.source.local.user.UserRepository
import com.calmperson.seedsstore.ui.state.AccountScreenState
import com.calmperson.seedsstore.ui.state.CartScreenState
import com.calmperson.seedsstore.ui.state.CheckoutScreenState
import com.calmperson.seedsstore.ui.state.OrderHistoryScreenState
import com.calmperson.seedsstore.ui.state.SeedListScreenState
import com.calmperson.seedsstore.ui.state.SeedScreenState
import com.calmperson.seedsstore.ui.state.SeedState
import com.calmperson.seedsstore.ui.state.SignInCallback
import com.calmperson.seedsstore.ui.state.SignUpCallback
import com.calmperson.seedsstore.ui.state.WishlistScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val seedRepository: SeedRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var user: User? = null
    private val seeds: MutableStateFlow<List<Seed>> = MutableStateFlow(emptyList())

    private val _seedListScreenState: MutableStateFlow<SeedListScreenState?> =
        MutableStateFlow(null)
    val categoryScreenState: StateFlow<SeedListScreenState?>
        get() = _seedListScreenState

    private val _seedScreenState: MutableStateFlow<SeedScreenState?> = MutableStateFlow(null)
    val seedScreenState: StateFlow<SeedScreenState?>
        get() = _seedScreenState

    private val _accountScreenState: MutableStateFlow<AccountScreenState?> = MutableStateFlow(null)
    val accountScreenState: StateFlow<AccountScreenState?>
        get() = _accountScreenState

    private val _cartScreenState: MutableStateFlow<CartScreenState?> = MutableStateFlow(null)
    val cartScreenState: StateFlow<CartScreenState?>
        get() = _cartScreenState

    private val _orderHistoryScreenState: MutableStateFlow<OrderHistoryScreenState?> =
        MutableStateFlow(null)
    val orderHistoryScreenState: StateFlow<OrderHistoryScreenState?>
        get() = _orderHistoryScreenState

    private val _wishlistScreenState: MutableStateFlow<WishlistScreenState?> =
        MutableStateFlow(null)
    val wishlistScreenState: StateFlow<WishlistScreenState?>
        get() = _wishlistScreenState

    private val _checkoutScreenState: MutableStateFlow<CheckoutScreenState?> =
        MutableStateFlow(null)
    val checkoutScreenState: StateFlow<CheckoutScreenState?>
        get() = _checkoutScreenState


    init {
        viewModelScope.launch {
            seedRepository.seeds.collect {
                seeds.value = it
            }
        }
    }


    fun updateSeedListScreenState(categoryId: Int) {
        viewModelScope.launch {
            SeedCategory.from(categoryId)?.let { category ->
                val seeds = seeds.value
                    .filter { seed -> seed.categories.contains(category) }
                    .map { it.asState() }
                if (seeds.isNotEmpty()) {
                    _seedListScreenState.value = SeedListScreenState(
                        category,
                        seeds,
                        user?.cart ?: emptyList(),
                        user?.wishList ?: emptyList()
                    )
                }
            }
        }
    }

    fun updateSeedScreenState(seedId: Long) {
        viewModelScope.launch {
            seeds.value.firstOrNull { it.id == seedId }?.let { seed ->
                _seedScreenState.update {
                    SeedScreenState(
                        seed,
                        seed.amount >= 5,
                        user?.wishList?.contains(seedId) ?: false,
                        user?.cart?.contains(seedId) ?: false
                    )
                }
            }
        }
    }

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): StateFlow<SignUpCallback?> {
        val callback = MutableStateFlow<SignUpCallback?>(null)
        val newUser = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            creditCard = null,
            address = null,
            orderHistory = mutableListOf(),
            wishList = mutableListOf(),
            cart = mutableListOf()
        )
        CoroutineScope(Dispatchers.IO).launch {
           if (userRepository.findByEmail(email) != null) {
               callback.value = SignUpCallback(false, R.string.user_already_exists)
           } else {
               val id = userRepository.insert(newUser)
               userRepository.findById(id)?.let { _user ->
                   _accountScreenState.value =
                       AccountScreenState(
                           firstName = _user.firstName,
                           lastName = _user.lastName,
                           email = _user.email,
                           creditCard = _user.creditCard,
                           address = _user.address,
                           orderHistory = seeds.filter(_user.orderHistory),
                           wishList = seeds.filter(_user.wishList)
                       )

                   _cartScreenState.value =
                       CartScreenState(seeds.filter(_user.cart).map { seed -> seed.asState() })
                   _orderHistoryScreenState.value = OrderHistoryScreenState(
                       seeds.filter(_user.orderHistory).map { seed -> seed.asState() })
                   _wishlistScreenState.value = WishlistScreenState(
                       seeds.filter(_user.wishList).map { seed -> seed.asState() })
                   _checkoutScreenState.value =
                       CheckoutScreenState(_user.creditCard?.number, _user.address)

                   callback.value = SignUpCallback(true, -1)
                   user = _user
               } ?: run {
                   callback.value = SignUpCallback(false, R.string.something_went_wrong)
               }
           }
        }
        return callback
    }

    fun signIn(email: String, password: String, rememberMe: () -> Unit): StateFlow<SignInCallback?> {

        if (user != null) return MutableStateFlow(SignInCallback(false, -1))

        val callback = MutableStateFlow<SignInCallback?>(null)
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.findByEmail(email)?.let { _user ->
                if (_user.password != password) {
                    callback.value =
                        SignInCallback(
                            false,
                            R.string.wrong_password
                        )

                } else {

                    _accountScreenState.value =
                        AccountScreenState(
                            firstName = _user.firstName,
                            lastName = _user.lastName,
                            email = _user.email,
                            creditCard = _user.creditCard,
                            address = _user.address,
                            orderHistory = seeds.filter(_user.orderHistory),
                            wishList = seeds.filter(_user.wishList)
                        )
                    _cartScreenState.value =
                        CartScreenState(seeds.filter(_user.cart).map { seed -> seed.asState() })
                    _orderHistoryScreenState.value = OrderHistoryScreenState(
                        seeds.filter(_user.orderHistory).map { seed -> seed.asState() })
                    _wishlistScreenState.value = WishlistScreenState(
                        seeds.filter(_user.wishList).map { seed -> seed.asState() })
                    _checkoutScreenState.value =
                        CheckoutScreenState(_user.creditCard?.number, _user.address)

                    callback.value = SignInCallback(true, -1)
                    user = _user
                    rememberMe.invoke()
                }
            } ?: run {
                callback.value = SignInCallback(
                    false,
                    R.string.user_not_found
                )

            }
        }
        return callback
    }

    fun logOut() {
        user = null
        _accountScreenState.value = null
    }

    fun updateUserData(
        firstName: String = user?.firstName ?: "",
        lastName: String = user?.lastName ?: "",
        email: String = user?.email ?: "",
        password: String = user?.password ?: "",
        creditCard: CreditCard? = user?.creditCard,
        address: String? = user?.address,
    ) {
        user?.let { _user ->
            _user.apply {
                this.firstName = firstName
                this.lastName = lastName
                this.email = email
                this.password = password
                this.creditCard = creditCard
                this.address = address

            }
            _accountScreenState.update { state ->
                AccountScreenState(
                    firstName,
                    lastName,
                    email,
                    creditCard,
                    address,
                    state?.orderHistory ?: emptyList(),
                    state?.wishList ?: emptyList()
                )
            }
            viewModelScope.launch(Dispatchers.IO) {
                user?.let {
                    userRepository.update(it)
                }
            }
        }
    }

    fun addToCart(seedId: Long) {
        user?.let { _user ->
            with(_user.cart) {
                if (contains(seedId)) remove(seedId) else add(seedId)
                _cartScreenState.update {
                    CartScreenState(seeds.filter(_user.cart).map { seed -> seed.asState() })
                }
                updateSeedScreenState(seedId)
                updateUserData()
            }
        }
    }

    fun addToWishList(seedId: Long) {
        user?.let { _user ->
            with(_user.wishList) {
                if (contains(seedId)) remove(seedId) else add(seedId)
                _wishlistScreenState.update {
                    WishlistScreenState(seeds.filter(_user.wishList).map { seed -> seed.asState() })
                }
                updateSeedScreenState(seedId)
                updateUserData()
            }
        }
    }

    fun buy() {
        user?.let { _user ->
            _cartScreenState.value?.let { state ->
                viewModelScope.launch(Dispatchers.IO) {
                    state.seeds.forEach { seedState ->
                        seedState.value.seed.amount = seedState.value.seed.amount - 5
                        seedRepository.update(seedState.value.seed)
                    }
                }
                _user.orderHistory.addAll(state.seeds.map { it.value.seed.id })
                _user.cart.clear()
                _orderHistoryScreenState.update {
                    OrderHistoryScreenState(
                        state.seeds.map { it.value.seed.asState() }
                    )
                }
                updateUserData()
                _cartScreenState.update {
                    CartScreenState(emptyList())
                }
            }
        }
    }

    private fun StateFlow<List<Seed>>.filter(ids: List<Long>): List<Seed> {
        return ids.map { id -> this.value.first { seed -> seed.id == id } }
    }

    private fun Seed.asState(): StateFlow<SeedState> {
        return MutableStateFlow(
            SeedState(
                this,
                this.amount >= 5,
                user?.wishList?.contains(this.id) ?: false,
                user?.cart?.contains(this.id) ?: false
            )
        )
    }
}