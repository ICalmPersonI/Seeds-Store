package com.calmperson.seedsstore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.calmperson.seedsstore.R
import com.calmperson.seedsstore.data.SeedCategory
import com.calmperson.seedsstore.data.Seed
import com.calmperson.seedsstore.ui.components.AppIconButton
import com.calmperson.seedsstore.ui.components.AppTextButton
import com.calmperson.seedsstore.ui.state.SeedScreenState
import com.calmperson.seedsstore.ui.theme.Green
import com.calmperson.seedsstore.ui.theme.LightViolet
import com.calmperson.seedsstore.ui.theme.Purple
import com.calmperson.seedsstore.ui.theme.RobotoBold
import com.calmperson.seedsstore.ui.theme.SeedsStoreTheme
import com.calmperson.seedsstore.ui.theme.SourceSansProRegular
import com.calmperson.seedsstore.ui.theme.SourceSansProSemiBold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
@Composable
fun SeedScreen(
    modifier: Modifier = Modifier,
    state: SeedScreenState,
    onAddToCartButtonClick: (Long) -> Unit,
    onAddToWishlistButtonClick: (Long) -> Unit,
    isUserLogged: Boolean
) {

    val descriptionScrollState = rememberScrollState()
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(360.dp)
                .fillMaxWidth(),
            model = "file:///android_asset/seed/${state.seed.image}",
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 281.dp)
                .zIndex(1f),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier.height(210.dp)
                ) {
                    Spacer(modifier = Modifier.height(37.dp))
                    Text(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        text = state.seed.name,
                        fontSize = 30.sp,
                        fontFamily = RobotoBold,
                        lineHeight = 41.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .height(45.dp)
                    ) {
                        Text(
                            text = "${state.seed.price}$",
                            fontSize = 32.sp,
                            fontFamily = RobotoBold,
                            lineHeight = 43.sp
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .align(Alignment.CenterVertically),
                            text = stringResource(R.string.for_seeds, 5),
                            fontSize = 24.sp,
                            fontFamily = SourceSansProRegular,
                            lineHeight = 43.sp,
                            color = Color(0xFF9586A8)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        text = stringResource(R.string.gr_bar, 150),
                        fontSize = 17.sp,
                        fontFamily = SourceSansProSemiBold,
                        lineHeight = 25.sp,
                        color = Green
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .weight(1f)
                        .verticalScroll(descriptionScrollState),
                    text = state.seed.description,
                    fontSize = 17.sp,
                    fontFamily = SourceSansProRegular,
                    lineHeight = 25.sp,
                    color = Purple
                )
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .height(56.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppIconButton(
                        modifier = Modifier.fillMaxHeight(),
                        painter = painterResource(R.drawable.heart),
                        shape = RoundedCornerShape(8.dp),
                        background = if (state.isInWishlist) Purple else Color.Transparent,
                        tint = if (state.isInWishlist) Color.White else Purple,
                        borderColor = LightViolet,
                        onClick = {
                            onAddToWishlistButtonClick.invoke(state.seed.id)
                        },
                        enabled = isUserLogged
                    )
                    Spacer(modifier = Modifier.width(21.dp))
                    AppTextButton(
                        modifier = Modifier.width(275.dp),
                        text = stringResource(R.string.add_to_cart).uppercase(),
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(R.drawable.shopping_cart),
                                contentDescription = null,
                                tint = if (!isUserLogged) Color.Gray
                                else if (state.isInCart) Green
                                else Color.White
                            )
                        },
                        onClick = {
                            onAddToCartButtonClick.invoke(state.seed.id)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = if (state.isInCart) Color.White else Green,
                            contentColor = if (state.isInCart) Green else Color.White
                        ),
                        enabled = isUserLogged
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
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
private fun SeedScreenPreview() {
    SeedsStoreTheme {
        SeedScreen(
            state = SeedScreenState(
                Seed(
                    "White Bamboo",
                    "Dendrocalamus membranaceus also known as White Bamboo and formerly classified as “Bambusa membranacea” is a medium-sized tropical clumping bamboo originating from Southeast Asia.",
                    "bamboo/white-bamboo-seeds-dendrocalamus-membranaceus.jpg",
                    2.95,
                    200,
                    listOf(SeedCategory.BAMBOO)
                ),
                true,
                false,
                false
            ),
            onAddToCartButtonClick = { },
            onAddToWishlistButtonClick = { },
            isUserLogged = true
        )
    }
}