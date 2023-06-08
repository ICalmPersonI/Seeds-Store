package com.calmperson.seedsstore.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.ui.state.SeedState
import com.calmperson.seedsstore.ui.theme.Green
import com.calmperson.seedsstore.ui.theme.LightViolet
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.RobotoBold
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SeedContainer(
    modifier: Modifier,
    state: State<SeedState>,
    onClick: () -> Unit,
    addToWishList: (Long) -> Unit,
    addToCart: (Long) -> Unit,
    isUserLogged: Boolean
) {
    Box(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(177.dp, 128.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, LightViolet, RoundedCornerShape(8.dp)),
                model = "file:///android_asset/seed/${state.value.seed.image}",
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(21.dp))
            Column {
                Text(
                    text = state.value.seed.name,
                    fontSize = 18.sp,
                    fontFamily = RobotoBold,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = state.value.seed.price.toString() + "$",
                    fontSize = 22.sp,
                    fontFamily = RobotoBold,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    AppIconButton(
                        painter = painterResource(R.drawable.heart),
                        shape = RoundedCornerShape(8.dp),
                        background = if (state.value.isInWishlist) Purple else Color.Transparent,
                        tint = if (state.value.isInWishlist) Color.White else Purple,
                        borderColor = LightViolet,
                        onClick = { addToWishList.invoke(state.value.seed.id) },
                        enabled = isUserLogged
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    AppIconButton(
                        painter = painterResource(R.drawable.shopping_cart),
                        shape = RoundedCornerShape(8.dp),
                        background = if (state.value.isInCart) Color.White else Green,
                        tint = if (state.value.isInCart) Green else Color.White,
                        borderColor = if (state.value.isInCart) Green else Color.Transparent,
                        onClick = { addToCart.invoke(state.value.seed.id) },
                        enabled = isUserLogged
                    )
                }
            }
        }
    }
}